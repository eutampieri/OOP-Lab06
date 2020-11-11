package it.unibo.oop.lab.exception1;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Models a generic Robot.
 * 
 */
public class Robot {

	// I commented out the next variable because, as I explained in consumeBatteryForMovement,
	// I don't think it's a good idea to fully drain the battery!
    // private static final int BATTERY_EMPTY = 0;
    private static final int MOVEMENT_DELTA = 1;
    private static final double MOVEMENT_DELTA_CONSUMPTION = 0.1;
    private static final double BATTERY_FULL = 100;

    private double batteryLevel;
    private final RobotEnvironment environment;
    private final String robotName;

    /**
     * 
     * @param robotName
     *            name of the robot
     * @param batteryLevel
     *            initial battery level
     * @throws Exception
     */
    public Robot(final String robotName, final double batteryLevel) {
        this.batteryLevel = batteryLevel;
        this.environment = new RobotEnvironment(new RobotPosition(0, 0));
        this.robotName = robotName;
    }

    /**
     * Moves the robot up by one unit.
     * 
     * @return If the Up movement has been performed
     */
    public void moveUp() throws PositionOutOfBoundException, NotEnoughBatteryException {
    	try {
    		moveToPosition(environment.getCurrPosX(), this.environment.getCurrPosY() + Robot.MOVEMENT_DELTA);
    	} catch(PositionOutOfBoundException |
    			NotEnoughBatteryException e) {
        	throw e;
        }
    }

    /**
     * Moves the robot down by one unit.
     * 
     * @return If the Down movement has been performed
     */
    public void moveDown() throws PositionOutOfBoundException, NotEnoughBatteryException {
        try {
        	this.moveToPosition(this.environment.getCurrPosX(), environment.getCurrPosY() - Robot.MOVEMENT_DELTA);
    	} catch(PositionOutOfBoundException |
    			NotEnoughBatteryException e) {
        	throw e;
        }
    }

    /**
     * Moves the robot left by one unit.
     * 
     * @return A boolean indicating if the Left movement has been performed
     */
    public void moveLeft() throws PositionOutOfBoundException, NotEnoughBatteryException {
    	try {
    		this.moveToPosition(this.environment.getCurrPosX() - Robot.MOVEMENT_DELTA,
                this.environment.getCurrPosY());
    	} catch(PositionOutOfBoundException |
    			NotEnoughBatteryException e) {
        	throw e;
        }
    }

    /**
     * Moves the robot right by one unit.
     * 
     * @return A boolean indicating if the Right movement has been performed
     */
    public void moveRight() throws PositionOutOfBoundException, NotEnoughBatteryException {
    	try {
    		this.moveToPosition(this.environment.getCurrPosX() + Robot.MOVEMENT_DELTA,
                this.environment.getCurrPosY());
    	} catch(PositionOutOfBoundException |
    			NotEnoughBatteryException e) {
        	throw e;
        }
    }

    /**
     * Fully recharge the robot.
     */
    public void recharge() {
        this.batteryLevel = BATTERY_FULL;
    }

    /**
     * move a robot to the specified position.
     * 
     * @param newX
     *            the new X position to move the robot to
     * @param newY
     *            the new Y position to move the robot to
     * @return true if robot gets moved, false otherwise
     */
    private void moveToPosition(final int newX, final int newY) 
    		throws PositionOutOfBoundException, NotEnoughBatteryException {
    	try {
    		this.consumeBatteryForMovement();
       		this.environment.move(newX, newY);
            this.log("Moved to position(" + newX + "," + newY + ").");
          } catch(NotEnoughBatteryException e) {
        	  throw e;
          } catch(PositionOutOfBoundException e) {
        	  // If we cannot move, then we undo the battery consumption
        	  this.consumeBattery(-Robot.MOVEMENT_DELTA_CONSUMPTION);
        	  throw e;
          }
    }

    /**
     * Consume the amount of energy required to move the robot substracting it
     * from the current battery level.
     */
    protected void consumeBatteryForMovement() {
        this.consumeBattery(Robot.MOVEMENT_DELTA_CONSUMPTION);
    }
    
    private double getRoundedBatteryValue() {
    	double digits = Math.log10(MOVEMENT_DELTA_CONSUMPTION) * -1 + 1;
    	return BigDecimal.valueOf(this.getBatteryLevel())
    			.setScale((int) digits, RoundingMode.HALF_UP)
    			.doubleValue();
    }

    /**
     * Consume the amount of energy provided in input from the battery.
     * 
     * @param amount
     *            the amount of battery energy to consume
     */
    protected void consumeBattery(final double amount) throws NotEnoughBatteryException {
        if (this.getRoundedBatteryValue() >= amount) {
            this.batteryLevel -= amount;
        } else {
        	// I don't set the battery to zero because the battery is not flat,
        	// it just doesn't have enough charge to perform this operation!
            throw new NotEnoughBatteryException(this.getRoundedBatteryValue());
        }
    }

    /**
     * 
     * @return The robot's current battery level
     */
    public double getBatteryLevel() {
        return (double) Math.round(this.batteryLevel * 100) / 100;
    }

    /**
     * 
     * @return The robot environment
     */
    public RobotEnvironment getEnvironment() {
        return this.environment;
    }

    /**
     * Log to stdout the string provided in input.
     * 
     * @param msg
     *            the msg to print
     */
    protected void log(final String msg) {
        System.out.println("[" + this.robotName + ":]" + msg);
    }
}
