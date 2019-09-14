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
  public static final int FWDSPEED = 200; // Default rotational speed of wheels
  public static final int DELTASPD = 150; // Bang-bang constant
  public static final int SLEEPINT = 50; // Sleep interval 50 mS = 20Hz

  @Override
  public void processUSData(int distance) {
    filter(distance);
    
    distError=distance - BAND_CENTER; // Compute error
    if (Math.abs(distError) <= BAND_WIDTH) { // Within limits, same speed
    LEFT_MOTOR.setSpeed(FWDSPEED); // Start moving forward
    RIGHT_MOTOR.setSpeed(FWDSPEED);
    //LEFT_MOTOR.forward();
    //RIGHT_MOTOR.forward();
    }
    else if (distError > 0) { // Too far
    LEFT_MOTOR.setSpeed(FWDSPEED + 20);
    RIGHT_MOTOR.setSpeed(FWDSPEED - 125);
    //LEFT_MOTOR.forward();
    //RIGHT_MOTOR.forward();
    }
    else if (distError < 0) { //too close
    LEFT_MOTOR.setSpeed(FWDSPEED-175);
    RIGHT_MOTOR.setSpeed(FWDSPEED + 50);
    //LEFT_MOTOR.forward();
   // RIGHT_MOTOR.forward();
    }
//    try {
//      Thread.sleep(SLEEPINT);
//    } catch (InterruptedException e) {
//      // TODO Auto-generated catch block
//      e.printStackTrace();
//    } // Allow other threads

    // TODO: process a movement based on the us distance passed in (BANG-BANG style)
  }

  @Override
  public int readUSDistance() {
    return this.distance;
  }
}
