package it.unibo.oop.lab.exception1;

/* This is an IllegalStateException because
 * the the robot is not broken or something,
 * the battery is flat or is not holding sufficient charge
 * to perform the operation. 
 */
public class NotEnoughBatteryException extends IllegalStateException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1131254828282511103L;
	private final double batteryLevel;
	
	public NotEnoughBatteryException(double batteryLevel) {
		super();
		this.batteryLevel = batteryLevel;
	}
	/**
     * 
     * @return the string representation of instances of this class
     */
    @Override
    public String toString() {
        return "The battery is flat (Battery level = " + this.batteryLevel + ")";
    }

    @Override
    public String getMessage() {
        return this.toString();
    }
}
