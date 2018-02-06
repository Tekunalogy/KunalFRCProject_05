package org.usfirst.frc.team3647.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends IterativeRobot
{
	Autonomous auto;
	private static final String kDefaultAuto = "Default";
	private static final String kCustomAuto = "My Auto";
	private String m_autoSelected;
	private SendableChooser<String> m_chooser = new SendableChooser<>();
	Joysticks controller = new Joysticks();
	Encoders enc = new Encoders();

	/**
	 * This function is run when the robot is first started up and should be used
	 * for any initialization code.
	 */
	@Override
	public void robotInit()
	{
		m_chooser.addDefault("Default Auto", kDefaultAuto);
		m_chooser.addObject("My Auto", kCustomAuto);
		SmartDashboard.putData("Auto choices", m_chooser);
		enc.resetEncoders();
		auto = new Autonomous();
		
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable chooser
	 * code works with the Java SmartDashboard. If you prefer the LabVIEW Dashboard,
	 * remove all of the chooser code and uncomment the getString line to get the
	 * auto name from the text box below the Gyro
	 *
	 * <p>
	 * You can add additional auto modes by adding additional comparisons to the
	 * switch structure below with additional strings. If using the SendableChooser
	 * make sure to add them to the chooser code above as well.
	 */
	@Override
	public void autonomousInit()
	{
		m_autoSelected = m_chooser.getSelected();
		// autoSelected = SmartDashboard.getString("Auto Selector",
		// defaultAuto);
		System.out.println("Auto selected: " + m_autoSelected);
		Autonomous.input = "start";
		enc.resetEncoders();
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic()
	{
		// enc.setEncoderValues();
		
		auto.runAuto(enc.getLeftEncoder(), enc.getRightEncoder(), enc);
		//System.out.println("Left Encoder: " + enc.getLeftEncoder() + " Right Encoder: " + enc.getRightEncoder());
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic()
	{
		controller.updateMainController();
		System.out.println("Left Encoder: " + enc.getLeftEncoder() + " Right Encoder: " + enc.getRightEncoder());
		if(Joysticks.leftJoySticky != 0)
		{
			Motors.driveForward(enc.getLeftEncoder(), enc.getRightEncoder(), Joysticks.leftJoySticky);
		}
		else if(Joysticks.rightJoyStickx != 0)
		{
			Motors.leftTalon.set(Joysticks.rightJoyStickx);
			Motors.rightTalon.set(Joysticks.rightJoyStickx);
		}
		else
		{
			Motors.driveForward(enc.getLeftEncoder(), enc.getRightEncoder(), 0);
			enc.resetEncoders();
		}
		
		
		
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic()
	{
	}

}