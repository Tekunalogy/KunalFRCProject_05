package org.usfirst.frc.team3647.robot;
import edu.wpi.first.wpilibj.Spark;

public class Motors
{
	public static Spark leftTalon = new Spark(2);
	public static Spark rightTalon = new Spark(1);	
	
	
	public static double rightSpeed;
	public static double leftSpeed;
	static double prevError = 0;
	static double sumError = 0;
	static double kp = 10;
	static double ki = 0.1;
	static double kd = 0.03;
	public static void runPIDforward(double lEnc, double rEnc, double turnForward) 
	{
		double error = (lEnc - rEnc - turnForward) / 1000; // scaling down the values to make them easier to
																		// interpret
		double diffError = error - prevError;// previous errors
		sumError = sumError + error;// sum of all the errors
		double inputValue = kp * error + ki * sumError + kd * diffError; // PID equation

		leftSpeed = .8 * leftSpeed - inputValue / 2; // 80% speed
		rightSpeed = .8 * rightSpeed + inputValue / 2;
		if (leftSpeed < 0) { // eliminates error where speed goes below zero
			leftSpeed = 0;
		}
		if (rightSpeed < 0) { // eliminates error where speed goes below zero
			rightSpeed = 0;
		}

		prevError = error; // to calculate all errors
		Motors.leftTalon.set(leftSpeed);
		Motors.rightTalon.set(-rightSpeed);
	}
	
	public static void runPIDbackward(double lEnc, double rEnc, double turnBackward) {
		double error = (lEnc - rEnc - turnBackward) / 1000;
		double diffError = error - prevError;
		sumError = sumError + error;
		double inputValue = kp * error + ki * sumError + kd * diffError;

		leftSpeed = .8 * leftSpeed - inputValue / 2;
		rightSpeed = .8 * rightSpeed + inputValue / 2;

		if (leftSpeed > 0) {
			leftSpeed = 0;
		}
		if (rightSpeed > 0) {
			rightSpeed = 0;
		}

		prevError = error;
		Motors.leftTalon.set(leftSpeed);
		Motors.rightTalon.set(-rightSpeed);
	}
	
	public static void turnRight(double lEnc, double rEnc, double leftSpeed, double rightSpeed)
	{
		double encRatio = (lEnc / rEnc);
		double speedRatio = Math.abs((leftSpeed / rightSpeed));
		if(encRatio > speedRatio)
		{
			if(encRatio >= speedRatio + 1)
			{
				Motors.leftTalon.set(leftSpeed - 0.25);
				Motors.rightTalon.set(rightSpeed);
			}
			else if(encRatio >= speedRatio + 0.5)
			{
				Motors.leftTalon.set(leftSpeed - 0.17);
				Motors.rightTalon.set(rightSpeed);
			}
			else if(encRatio >= speedRatio + 0.25)
			{
				Motors.leftTalon.set(leftSpeed - 0.1);
				Motors.rightTalon.set(rightSpeed);
			}
			else
			{
				Motors.leftTalon.set(leftSpeed);
				Motors.rightTalon.set(rightSpeed);
			}
			
		}
		else if(encRatio < speedRatio)
		{
			if(encRatio <= speedRatio + 0.25)
			{
				Motors.leftTalon.set(leftSpeed);
				Motors.rightTalon.set(rightSpeed + 0.1);
			}
			else if(encRatio <= speedRatio + 0.5)
			{
				Motors.leftTalon.set(leftSpeed);
				Motors.rightTalon.set(rightSpeed + 0.17);
			}
			else if(encRatio <= speedRatio + 1)
			{
				Motors.leftTalon.set(leftSpeed);
				Motors.rightTalon.set(rightSpeed + 0.25);
			}
			else
			{
				Motors.leftTalon.set(leftSpeed);
				Motors.rightTalon.set(rightSpeed + .3);
			}
		}
		else
		{
			Motors.leftTalon.set(leftSpeed);
			Motors.rightTalon.set(rightSpeed);
		}
	}
	
	public static void turnRightBackward(double lEnc, double rEnc, double leftSpeed, double rightSpeed)
	{
		double encRatio = (lEnc / rEnc);
		double speedRatio = Math.abs((leftSpeed / rightSpeed));
		if(encRatio > speedRatio)
		{
			if(encRatio >= speedRatio + 1)
			{
				Motors.leftTalon.set(leftSpeed + 0.25);
				Motors.rightTalon.set(rightSpeed);
			}
			else if(encRatio >= speedRatio + 0.5)
			{
				Motors.leftTalon.set(leftSpeed + 0.17);
				Motors.rightTalon.set(rightSpeed);
			}
			else if(encRatio >= speedRatio + 0.25)
			{
				Motors.leftTalon.set(leftSpeed + 0.1);
				Motors.rightTalon.set(rightSpeed);
			}
			else
			{
				Motors.leftTalon.set(leftSpeed);
				Motors.rightTalon.set(rightSpeed);
			}
			
		}
		else if(encRatio < speedRatio)
		{
			if(encRatio <= speedRatio + 0.25)
			{
				Motors.leftTalon.set(leftSpeed);
				Motors.rightTalon.set(rightSpeed - 0.1);
			}
			else if(encRatio <= speedRatio + 0.5)
			{
				Motors.leftTalon.set(leftSpeed);
				Motors.rightTalon.set(rightSpeed - 0.17);
			}
			else if(encRatio <= speedRatio + 1)
			{
				Motors.leftTalon.set(leftSpeed);
				Motors.rightTalon.set(rightSpeed - 0.25);
			}
			else
			{
				Motors.leftTalon.set(leftSpeed);
				Motors.rightTalon.set(rightSpeed - .3);
			}
		}
		else
		{
			Motors.leftTalon.set(leftSpeed);
			Motors.rightTalon.set(rightSpeed);
		}
	}
	
	public static void driveBackward(double lEnc, double rEnc, double speed)
	{
		double encDiff = Math.abs(Math.abs(lEnc) - Math.abs(rEnc));
		if(encDiff < 5)
		{
			//tankDrive.tankDrive(speed, speed, false);
			leftTalon.set(speed);
			rightTalon.set(-speed);
		}
		else if(encDiff < 100)
		{
			if(lEnc < rEnc)
		 	{
				leftTalon.set(speed + .075);
				rightTalon.set(-speed);
				//tankDrive.tankDrive(speed - .05, speed, false);
		 	} 
			else
		 	{
				leftTalon.set(speed);
				rightTalon.set(-speed - .075);
		 		//tankDrive.tankDrive(speed, speed - .05, false);
		 	}
		 }
		 else if(encDiff < 150)
		 {
			 if(lEnc < rEnc)
			 {
				leftTalon.set(speed + .15);
				rightTalon.set(-speed);
				//tankDrive.tankDrive(speed - .1, speed, false);
			 }
			 else
			 {
				leftTalon.set(speed);
				rightTalon.set(-speed - .15);
				//tankDrive.tankDrive(speed, speed - .1, false);
			 }
		 }
		 else if(encDiff < 300)
		 {
			 if(lEnc < rEnc)
			 {
				leftTalon.set(speed + .25);
				rightTalon.set(-speed); 
				//tankDrive.tankDrive(speed - .18, speed, false);
			 }
			 else
			 {
				leftTalon.set(speed);
				rightTalon.set(-speed - .25);
				//tankDrive.tankDrive(speed, speed - .18, false);
			 }
		 }
		 else
		 {
			 if(lEnc < rEnc)
			 {
				leftTalon.set(speed + .3);
				rightTalon.set(-speed);
				//tankDrive.tankDrive(speed - .27, speed, false);
			 }
			 else
			 {
				 leftTalon.set(speed);
				 rightTalon.set(-speed - .3);
				 
				 //tankDrive.tankDrive(speed, speed - .27, false);
			 }
		 }
	}
	
	public static void driveForward(double lEnc, double rEnc, double speed)
	{
		double encDiff = Math.abs(Math.abs(lEnc) - Math.abs(rEnc));
		
		if(encDiff < 5)
		{
			//tankDrive.tankDrive(speed, speed, false);
			leftTalon.set(speed);
			rightTalon.set(-speed);
		}
		else if(encDiff < 100)
		{
			if(lEnc > rEnc)
		 	{
				leftTalon.set(speed - .05);
				rightTalon.set(-speed);
				//tankDrive.tankDrive(speed - .05, speed, false);
		 	}
			else
		 	{
				leftTalon.set(speed);
				rightTalon.set(-(speed - .05));
		 		//tankDrive.tankDrive(speed, speed - .05, false);
		 	}
		 }
		 else if(encDiff < 200)
		 {
			 if(lEnc > rEnc)
			 {
				leftTalon.set(speed - .1);
				rightTalon.set(-speed);
				//tankDrive.tankDrive(speed - .1, speed, false);
			 }
			 else
			 {
				leftTalon.set(speed);
				rightTalon.set(-(speed - .1));
				//tankDrive.tankDrive(speed, speed - .1, false);
			 }
		 }
		 else if(encDiff < 350)
		 {
			 if(lEnc > rEnc)
			 {
				leftTalon.set(speed - .2);
				rightTalon.set(-speed); 
				//tankDrive.tankDrive(speed - .18, speed, false);
			 }
			 else
			 {
				leftTalon.set(speed);
				rightTalon.set(-(speed - .2));
				//tankDrive.tankDrive(speed, speed - .18, false);
			 }
		 }
		 else
		 {
			 if(lEnc > rEnc)
			 {
				leftTalon.set(speed - .25);
				rightTalon.set(speed);
				//tankDrive.tankDrive(speed - .27, speed, false);
			 }
			 else
			 {
				 leftTalon.set(speed);
				 rightTalon.set(-(speed - .25));
				 
				 //tankDrive.tankDrive(speed, speed - .27, false);
			 }
		 }
	}
	
	public static void PIDDrive(double lEnc, double rEnc, double speed)
	{
		if(speed == 0)
		{
			leftTalon.set(0);
			rightTalon.set(0);
//			sumError = 0;
//			prevError = 0;
			
		}
		else
		{
			double kP = 10;
			double kI = 0.1;
			double kD = 0.03;
			double error =  lEnc - rEnc;
			
//			sumError += error;
//			double diffError = error - prevError;
			
//			double speedInputValue = (kP * error) + (kI * sumError) + (kD * diffError);
			
//			leftTalon.set(speed - (speedInputValue / 2));
//			rightTalon.set((-speed) - (speedInputValue / 2));
		}
	}

	public static void zero()
	{
		leftTalon.set(0);
		rightTalon.set(0);
	}
	
}