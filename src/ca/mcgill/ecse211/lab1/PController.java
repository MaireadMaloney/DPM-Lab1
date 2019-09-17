package ca.mcgill.ecse211.lab1;

import static ca.mcgill.ecse211.lab1.Resources.*;

public class PController extends UltrasonicController {
  /**
   * The distance, either positive or negative from the BAND_CENTER (cm).
   */
  public static int distError = 0;
  /**
   * The default rotation speed of the wheels in Deg/s.
   */
  public static final int FWDSPEED = 150; // Default rotational speed of wheels


  public PController() {
    LEFT_MOTOR.setSpeed(MOTOR_HIGH); // Initialize motor rolling forward
    RIGHT_MOTOR.setSpeed(MOTOR_HIGH);
    LEFT_MOTOR.forward();
    RIGHT_MOTOR.forward();
  }

  @Override
  public void processUSData(int distance) {
    filter(distance);
    int deltaspeed = 2;
    distError = distance - P_BAND_CENTER; // Compute error
    int speedChange = Math.abs(deltaspeed * distError);

    if (distance == 2147483647) { // Handles bad reading
      LEFT_MOTOR.backward();
      LEFT_MOTOR.setSpeed(MOTOR_HIGH + (50));
      RIGHT_MOTOR.setSpeed((MOTOR_HIGH) + 100);
    }

    // CASE 1: IN RANGE
    if (BAND_WIDTH >= Math.abs(distError)) {
      LEFT_MOTOR.setSpeed(MOTOR_HIGH); // Start moving forward
      RIGHT_MOTOR.setSpeed(MOTOR_HIGH);
      RIGHT_MOTOR.forward();
      LEFT_MOTOR.forward();
    }

    // CASE 2: TOO FAR
    else if (distError > 3) { // Too far from the wall, change wheel speeds based on magnitude of error
      LEFT_MOTOR.forward();
      LEFT_MOTOR.setSpeed(MOTOR_HIGH + (speedChange) - 40);
      RIGHT_MOTOR.setSpeed(MOTOR_HIGH - (speedChange));
    }

    // CASE 3: TOO CLOSE
    else if (distError < 0) { // Too close to the wall, change wheel speeds based on magnitude of error
      LEFT_MOTOR.forward();
      RIGHT_MOTOR.setSpeed(MOTOR_HIGH + (speedChange) + 115);
      LEFT_MOTOR.setSpeed(MOTOR_HIGH - (speedChange));
    }

    if (distError < -18) { // Very close to wall, changes robot direction away from wall
      LEFT_MOTOR.backward();
      LEFT_MOTOR.setSpeed(MOTOR_HIGH + (50));
      RIGHT_MOTOR.setSpeed(MOTOR_HIGH + 100);
      RIGHT_MOTOR.forward();
    }
  }

  @Override
  public int readUSDistance() {
    return this.distance;
  }
}
