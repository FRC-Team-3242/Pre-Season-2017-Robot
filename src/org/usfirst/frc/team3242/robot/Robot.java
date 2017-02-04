package org.usfirst.frc.team3242.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.ctre.CANTalon;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	final String defaultAuto = "Default";
	final String customAuto = "My Auto";
	String autoSelected;
	SendableChooser<String> chooser = new SendableChooser<>();
	Talon vacuumMotor;
	Talon elevatorMotor;
	Talon shooterMotor;
	
    Joystick controller;
    CANTalon motorLeft;
    CANTalon motorRight;
    double yAxisLeft;
    double yAxisRight;
    double rightTrigger;
    double leftTrigger;
    RobotDrive driver;
    

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		chooser.addDefault("Default Auto", defaultAuto);
		chooser.addObject("My Auto", customAuto);
		SmartDashboard.putData("Auto choices", chooser);
		
		//driver.setInvertedMotor(kFrontLeft, true);
		
		controller = new Joystick(0);
		motorLeft = new CANTalon(2);
		motorRight = new CANTalon(1);
		driver = new RobotDrive(motorLeft, motorRight); // remember to pass objects for CAN, not port numbers
		vacuumMotor = new Talon(0);
		elevatorMotor = new Talon(2);
		shooterMotor = new Talon(1);
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString line to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the
	 * switch structure below with additional strings. If using the
	 * SendableChooser make sure to add them to the chooser code above as well.
	 */
	@Override
	public void autonomousInit() {
		autoSelected = chooser.getSelected();
		// autoSelected = SmartDashboard.getString("Auto Selector",
		// defaultAuto);
		System.out.println("Auto selected: " + autoSelected);
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		switch (autoSelected) {
		case customAuto:
			// Put custom auto code here
			break;
		case defaultAuto:
		default:
			// Put default auto code here
			break;
		}
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		double yAxisLeft = controller.getRawAxis(1);//LY
		double yAxisRight = controller.getRawAxis(5);//RY
		
		double leftTrigger = controller.getRawAxis(2);//L2
		double rightTrigger = controller.getRawAxis(3);//R2
		double triggers = rightTrigger + -leftTrigger;
		
		if(controller.getRawButton(5)){//L1
			shooterMotor.set(-1.0);//clockwise
		}else{
			shooterMotor.set(0);
		}
		
		if(controller.getRawButton(6)){//R1
			elevatorMotor.set(0.70);//counterclockwise
		}else{
			elevatorMotor.set(0);
		}
		
		driver.tankDrive(yAxisLeft, yAxisRight);
		vacuumMotor.set(triggers);
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
	}
}

