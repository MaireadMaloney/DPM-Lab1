package ca.mcgill.ecse211.lab1;

import static ca.mcgill.ecse211.lab1.Resources.*;

public class PController2 extends UltrasonicController {

  public static int distError = 0; // Error (amount to close or too far in meters
  public static final int FWDSPEED = 100; // Default rotational speed of wheels
  public static final int BAND = 28;
  public static final int BAND_W = 5;


  public PController2() {
    LEFT_MOTOR.setSpeed(FWDSPEED); // Initialize motor rolling forward
    RIGHT_MOTOR.setSpeed(FWDSPEED);
    LEFT_MOTOR.forward();
    RIGHT_MOTOR.forward();
  }

  @Override
  public void processUSData(int distance) {
    filter(distance);
    int deltaSpeedSlow = 3;
    int deltaSpeedFast = 4;
    distError = distance - BAND; // Compute error
    int speedChangeSlow = Math.abs(deltaSpeedSlow * distError);
    int speedChangeFast = Math.abs(deltaSpeedFast * distError);

    if (distError == 2147483647) { // handles bad reading
      distError = -25;
    }
    // CASE 1: within range
    if (BAND_W >= Math.abs(distError)) {
      LEFT_MOTOR.setSpeed(FWDSPEED);// Start moving forward
      RIGHT_MOTOR.setSpeed(FWDSPEED);
      RIGHT_MOTOR.forward();
      LEFT_MOTOR.forward();
      System.out.println("Striaght " + distance);
    }

    // CASE 2: too far from the wall
    else if (distError > 0) { // Too far from the wall, change wheel speeds based on magnitude of error
      if (distError > 70) { // Putting cap on speed change
        distError = 70;
        speedChangeSlow = deltaSpeedSlow * distError;
        speedChangeFast = deltaSpeedFast * distError;
      }
      LEFT_MOTOR.setSpeed(FWDSPEED + (speedChangeSlow));
      RIGHT_MOTOR.setSpeed(FWDSPEED - (speedChangeSlow));
      System.out.println("Right " + distance);
      // System.out.println(distError);
      // System.out.println(FWDSPEED+speedChangeSlow);
      // System.out.println(FWDSPEED-speedChangeFast);
    }


    else if (distError < 0) { // Too close to the wall, change wheel speeds based on magnitude of error
      if (distError < 0) {
        RIGHT_MOTOR.setSpeed(80);
        RIGHT_MOTOR.backward();
      }
      RIGHT_MOTOR.setSpeed(FWDSPEED + (speedChangeFast));
      RIGHT_MOTOR.backward();
      LEFT_MOTOR.setSpeed(FWDSPEED - (speedChangeFast));
      System.out.println("Left " + distance);
      // System.out.println(distError);
      // System.out.println(FWDSPEED+speedChangeFast);
      // System.out.println(FWDSPEED-speedChangeFast);
    }
  }



  @Override
  public int readUSDistance() {
    return this.distance;
  }

}
