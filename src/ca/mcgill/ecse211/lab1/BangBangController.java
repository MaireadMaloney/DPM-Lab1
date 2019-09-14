package ca.mcgill.ecse211.lab1;

import static ca.mcgill.ecse211.lab1.Resources.*;

public class BangBangController extends UltrasonicController {

  public BangBangController() {
    LEFT_MOTOR.setSpeed(MOTOR_HIGH); // Start robot moving forward
    RIGHT_MOTOR.setSpeed(MOTOR_HIGH);
    LEFT_MOTOR.forward();
    RIGHT_MOTOR.forward();
  }
  
  public static int distError=0; // Error (amount to close or too far in meters
  public static final int FWDSPEED = 250; // Default rotational speed of wheels
  public static final int BANGBANG_RIGHT = 145; //
  public static final int BANGBANG_LEFT = 225;
  @Override
  public void processUSData(int distance) {
    filter(distance);
    
    distError=distance - BAND_CENTER; // Compute error
    if (Math.abs(distError) <= BAND_WIDTH) { // Within limits, same speed
    LEFT_MOTOR.setSpeed(FWDSPEED); // Start moving forward
    RIGHT_MOTOR.setSpeed(FWDSPEED);
    }
    else if (distError > 0) { // Robot is too far, slow inside wheel to get closer to wall
    LEFT_MOTOR.setSpeed(FWDSPEED);
    RIGHT_MOTOR.setSpeed(FWDSPEED - BANGBANG_RIGHT);
    }
    else if (distError < 0) { //Robot is too close, slow outside wheel to move away from wall
    LEFT_MOTOR.setSpeed(FWDSPEED - BANGBANG_LEFT);
    RIGHT_MOTOR.setSpeed(FWDSPEED);
    }
  }

  @Override
  public int readUSDistance() {
    return this.distance;
  }
}
