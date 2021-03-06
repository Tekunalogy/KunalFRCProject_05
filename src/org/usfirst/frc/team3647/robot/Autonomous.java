package org.usfirst.frc.team3647.robot;

import edu.wpi.first.wpilibj.Timer;

public class Autonomous 
{
	String autoSelected = "middleAuto";
	public static String input;
	
	public void runAuto(double lEnc, double rEnc, Encoders enc)
	{
		if(autoSelected.equals("middleAuto"))
		{
			middleAuto(lEnc, rEnc, enc);
		}
		else if(autoSelected.equals("rightAuto"))
		{
			rightAuto(lEnc, rEnc, enc);
		}
		else if(autoSelected.equals("leftAuto"))
		{
			leftAuto(lEnc, rEnc);
		}
		else
		{
			Motors.zero();
		}
	}
	
	
	
	public void middleAuto(double lEnc, double rEnc, Encoders enc)
	{
		double speedToSet = 0.7;
		switch(input)
		{
			case("start"):
				if(lEnc < 5000)
				{	
					Motors.leftSpeed = speedToSet;
					Motors.rightSpeed = speedToSet;
					Motors.runPIDforward(lEnc, rEnc, 0);
					System.out.println("Left Encoder " + lEnc);
				}
				else
				{
					
					Motors.zero();
					input = "backStraight";
					System.out.println(input);
					Timer.delay(1);
					enc.resetEncoders();
				}
				break;
				
			case("backStraight"):
				if(lEnc > -4500)
				{
					Motors.leftSpeed = -speedToSet;
					Motors.rightSpeed = -speedToSet;
					Motors.runPIDbackward(lEnc, rEnc, 0);

				}
				else
				{
					Motors.zero();
					Motors.driveForward(lEnc, rEnc, 0);
					input = "Robot Has Stopped";
					System.out.println("Left Encoder " + lEnc);
					System.out.println(input);
				}
				break;
		}
		
	}
	
	public void rightAuto(double lEnc, double rEnc, Encoders enc)
	{
		double speedToSet = 0.7;
		switch(input)
		{
			case("start"):
				if(lEnc < 3000)
				{
					Motors.leftSpeed = speedToSet;
					Motors.rightSpeed = speedToSet;
					Motors.runPIDforward(lEnc, rEnc, 0);
					System.out.println("Left Encoder " + lEnc);
				}
				else
				{
					Motors.zero();
					input = "turn";
					System.out.println(input);
					Timer.delay(1);
					enc.resetEncoders();
				}
				break;
				
			case("turn"):
				if(lEnc < 2000)
				{
					if(lEnc + rEnc <= 10)
					{
						Motors.leftTalon.set(0.6);
						Motors.rightTalon.set(-0.3);
					}
					else
					{
						Motors.turnRight(lEnc, rEnc, 0.6, -0.3);
					}
					
				}
				else
				{
					Motors.zero();
					input = "backTurn";
					System.out.println(input);
					Timer.delay(1);
					System.out.println("Left Encoder " + lEnc);
					enc.resetEncoders();
				}
				break;
			
			case("backTurn"):
				if(lEnc > -2000)
				{
					if(lEnc + rEnc >= -10)
					{
						Motors.leftTalon.set(-.6);
						Motors.rightTalon.set(0.3);
					}
					else
					{
						Motors.turnRightBackward(lEnc, rEnc, -0.6, 0.3);
					}
				}
				else
				{
					Motors.zero();
					input = "backStraight";
					System.out.println(input);
					Timer.delay(2);
					System.out.println("Left Encoder " + lEnc);
					enc.resetEncoders();
				}
				break;
				
			case("backStraight"):
				if(lEnc > -3000)
				{
					Motors.leftSpeed = -speedToSet;
					Motors.rightSpeed = -speedToSet;
					Motors.runPIDbackward(lEnc, rEnc, 0);

				}
				else
				{
					Motors.zero();
					input = "Robot Has Stopped";
					System.out.println("Left Encoder " + lEnc);
					System.out.println(input);
				}
				break;
		}
	}
	
	public void leftAuto(double lEnc, double rEnc)
	{
		
	}
}
