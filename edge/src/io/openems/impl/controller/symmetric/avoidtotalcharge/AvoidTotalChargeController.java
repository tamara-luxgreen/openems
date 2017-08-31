package io.openems.impl.controller.symmetric.avoidtotalcharge;

/**
 * Created by maxo2 on 29.08.2017.
 */
import java.util.*;

import io.openems.api.channel.ConfigChannel;
import io.openems.api.controller.Controller;
import io.openems.api.device.nature.ess.EssNature;
import io.openems.api.device.nature.meter.MeterNature;
import io.openems.api.doc.ConfigInfo;
import io.openems.api.doc.ThingInfo;
import io.openems.api.exception.InvalidValueException;
import io.openems.api.exception.WriteChannelException;
import io.openems.api.security.User;
import io.openems.impl.controller.symmetric.avoidtotalcharge.Ess.State;
import io.openems.impl.controller.symmetric.balancing.Meter;

@ThingInfo(title = "Avoid total charge of battery. (Symmetric)", description = "Provides control over the battery's maximum state of charge at a specific time of day. For symmetric Ess.")
public class AvoidTotalChargeController extends Controller {

    /*
     * Config
     */
    @ConfigInfo(title = "Ess", description = "Sets the Ess devices.", type = Ess.class, isArray = true)
    public final ConfigChannel<Set<Ess>> esss = new ConfigChannel<Set<Ess>>("esss", this);

    @ConfigInfo(title = "Grid Meter", description = "Sets the grid meter.", type = io.openems.impl.controller.symmetric.avoidtotalcharge.Meter.class, isOptional = false, isArray = false)
    public final ConfigChannel<io.openems.impl.controller.symmetric.avoidtotalcharge.Meter> gridMeter = new ConfigChannel<>("gridMeter", this);

    @ConfigInfo(title = "Production Meters", description = "Sets the production meter.", type = io.openems.impl.controller.symmetric.avoidtotalcharge.Meter.class, isOptional = false, isArray = true)
    public final ConfigChannel<Set<io.openems.impl.controller.symmetric.avoidtotalcharge.Meter>> productionMeters = new ConfigChannel<>("productionMeters", this);

    @ConfigInfo(title = "Graph 1", description = "Sets the socMaxVals.", defaultValue = "[100,100,100,100,100,60,60,60,60,60,60,60,70,80,90,100,100,100,100,100,100,100,100,100]", type = Long[].class, isArray = true, accessLevel = User.OWNER)
    public final ConfigChannel<Long[]> graph1 = new ConfigChannel<>("graph1", this);
    //TODO: implement fixed length and min/max values (accessible by OWNER !)

    @ConfigInfo(title = "Graph 2", description = "Sets the socMaxVals.", defaultValue = "[100,100,100,100,100,60,60,60,60,60,60,60,60,60,70,80,90,100,100,100,100,100,100,100]", type = Long[].class, isArray = true, accessLevel = User.OWNER)
    public final ConfigChannel<Long[]> graph2 = new ConfigChannel<>("graph2", this);
    //TODO: implement fixed length and min/max values (accessible by OWNER !)

    @ConfigInfo(title = "Critical Percentage", description = "If the productionMeter's power raises above this percentage of its peak value, the graph-value may be neglected.", type = Long.class, accessLevel = User.OWNER, defaultValue = "100", isArray = false)
    public final ConfigChannel<Long> criticalPercentage = new ConfigChannel<Long>("criticalPercentage", this);
    //TODO: implement min/max values (accessible by OWNER !)

    @ConfigInfo(title = "Graph 1 active", description = "Activate Graph 1 (If no graph is activated, all values are set to 100)", defaultValue = "true" ,type = Boolean.class, accessLevel = User.OWNER, isArray = false, isOptional = false)
    public final ConfigChannel<Boolean> graph1active = new ConfigChannel<>("graph1active", this);

    @ConfigInfo(title = "Graph 2 active", description = "Activate Graph 2 (If no graph is activated, all values are set to 100)", defaultValue = "false" ,type = Boolean.class, accessLevel = User.OWNER, isArray = false, isOptional = false)
    public final ConfigChannel<Boolean> graph2active = new ConfigChannel<>("graph2active", this);



    /*
     * Constructors
     */
    public AvoidTotalChargeController() {
        super();
    }

    public AvoidTotalChargeController(String thingId) {
        super(thingId);
    }

    /*
     * Methods
     */
    @Override
    public void run() {

        try {
            /**
             * calculate the average available charging power
             */
            Long avgAllowedCharge = 0L;

            for (Ess ess : esss.value()) {
                avgAllowedCharge += ess.allowedCharge.value();
            }
            avgAllowedCharge = avgAllowedCharge / esss.value().size();

            /**
             * generate ChargingGraph and get maxWantedSoc value
             */
            int graphMode = 0;
            Optional<Boolean> g1aOptional = graph1active.valueOptional();
            Optional<Boolean> g2aOptional = graph2active.valueOptional();

            if (g1aOptional.isPresent() && g1aOptional.get()){
                graphMode = 1;
            } else if (g2aOptional.isPresent() && g2aOptional.get()){
                graphMode = 2;
            }

            Map<Integer, Double> m = new HashMap<Integer, Double>(0);
            for (int i = 0; i < 24; i++) {
                if (graphMode == 1){
                    m.put(i, (double) graph1.value()[i] / 100.0);
                }else if (graphMode == 2){
                    m.put(i, (double) graph2.value()[i] / 100.0);
                }else {
                    m.put(i, 1.0);
                }
            }
            ManualGraph mg = new ManualGraph(m);
            Long maxWantedSoc = (long) (100 * mg.getCurrentVal());

            /**
             * get the power feeded to the grid relatively to the producer's peak value
             */
            Long maxAbsoluteProducedPower = 0L;
            Long relativeFeededPower = 0L;

            for (io.openems.impl.controller.symmetric.avoidtotalcharge.Meter meter : productionMeters.value()){
                maxAbsoluteProducedPower += meter.maxActivePower.value();
            }

            relativeFeededPower = -100 * gridMeter.value().activePower.value() / maxAbsoluteProducedPower;

            for (Ess ess : esss.value()) {


                /**
                 * check if state of charge is above the value specified by the user and deny charging in
                 * case. However, in case the critical percentage was reached by the
                 * relativeFeededPower and the spareProducedPower is negative (-> grid is taking the
                 * maxFeedablePower) allow charging nevertheless.
                 */
                if (ess.soc.value() >= maxWantedSoc) {
                    if(relativeFeededPower >= criticalPercentage.value()) {
                        double factor = (double) criticalPercentage.value() / (double) 100;
                        double maxFeedablePower = factor * (double) maxAbsoluteProducedPower;
                        Long spareProducedPower = (long) ((double) gridMeter.value().activePower.value() + maxFeedablePower);
                        if (spareProducedPower < 0L){
                            try {
                                Long totalActivePower = (long) (((double) ess.allowedCharge.value() / (double) avgAllowedCharge) * ((double) spareProducedPower / (double) esss.value().size()));
                                ess.setActivePower.pushWrite(totalActivePower);
                            } catch (Exception e) {
                                log.error(e.getMessage(),e);
                            }
                        } else {
                            try {
                                ess.setActivePower.pushWriteMin(0L);
                            } catch (Exception e) {
                                log.error(e.getMessage(),e);
                            }
                        }
                    } else {
                        try {
                            ess.setActivePower.pushWriteMin(0L);
                        } catch (Exception e) {
                            log.error(e.getMessage(),e);
                        }
                    }
                }
            }

        } catch (InvalidValueException | IndexOutOfBoundsException e){
            log.error(e.getMessage(),e);
        }
    }
}