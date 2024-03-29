package ca.mcgill.ecse211.lab1;

import static ca.mcgill.ecse211.lab1.Resources.*;

public class BangBangController extends UltrasonicController {

  public BangBangController() {
    LEFT_MOTOR.setSpeed(MOTOR_HIGH); // Start robot moving forward
    RIGHT_MOTOR.setSpeed(MOTOR_HIGH);
    LEFT_MOTOR.forward();
    RIGHT_MOTOR.forward();
  }
  /**
   * The distance, either positive or negative from the BAND_CENTER (cm).
   */
  public static int distError = 0;
  /**
   * The default rotation speed of the wheels in Deg/s.
   */
  public static final int FWDSPEED = 200;
  /**
   * Bang Bang constant for the right motor in Deg/s.
   */
  public static final int BANGBANG_RIGHT = 145;
  /**
   * Bang Bang constant for the left motor in Deg/s.
   */
  public static final int BANGBANG_LEFT = 225;
  
  
  @Override
  public void processUSData(int distance) {
    filter(distance);
    
    distError=distance - B_BAND_CENTER; // Compute error
    if (Math.abs(distError) <= BAND_WIDTH) { // Within limits, same speed
    LEFT_MOTOR.setSpeed(FWDSPEED); // Start moving forward
    RIGHT_MOTOR.setSpeed(FWDSPEED);
    
    if (distance == 2147483647) { //handles bad reading
      LEFT_MOTOR.backward();
      LEFT_MOTOR.setSpeed(MOTOR_HIGH+(50));
      RIGHT_MOTOR.setSpeed((MOTOR_HIGH)+100);
      System.out.println("weird value");
  }
    
    }
    else if (distError > 3) { // Robot is too far, slow inside wheel to get closer to wall
      LEFT_MOTOR.forward();
     LEFT_MOTOR.setSpeed(FWDSPEED-50);
    RIGHT_MOTOR.setSpeed(FWDSPEED - BANGBANG_RIGHT);
    
    }
    else if (distError < 0) { //Robot is too close, slow outside wheel to move away from wall
      LEFT_MOTOR.forward();
      LEFT_MOTOR.setSpeed(FWDSPEED - BANGBANG_LEFT);
    RIGHT_MOTOR.setSpeed(FWDSPEED+50);
    
    }
    if (distError < -18) {
      // RIGHT_MOTOR.setSpeed((MOTOR_HIGH+(speedChange)));
      LEFT_MOTOR.backward();
      LEFT_MOTOR.setSpeed(MOTOR_HIGH+(50));
      RIGHT_MOTOR.setSpeed(MOTOR_HIGH+BANGBANG_RIGHT);
      RIGHT_MOTOR.forward();
      System.out.println("close");

    }
    
  }

  @Override
  public int readUSDistance() {
    return this.distance;
  }
}
