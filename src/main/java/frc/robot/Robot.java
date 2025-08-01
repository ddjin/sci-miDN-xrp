// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.xrp.XRPMotor;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.xrp.XRPServo;


/**
 * The methods in this class are called automatically corresponding to each mode, as described in
 * the TimedRobot documentation. If you change the name of this class or the package after creating
 * this project, you must also update the Main.java file in the project.
 */
public class Robot extends TimedRobot {
  private XboxController m_controller = new XboxController(0);
  private XRPMotor m_leftMotor = new XRPMotor(0);
  private XRPMotor m_rightMotor = new XRPMotor(1);

// Nicole's code//
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  private final DifferentialDrive mDrive = new DifferentialDrive(m_leftMotor, m_rightMotor);

  private final Timer mTimer = new Timer();
  private final XRPServo backServo = new XRPServo(deviceNum:4);

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  public Robot() {
    m_rightMotor.setInverted(true);
  }

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {}

  /**
   * This autonomous (along with the chooser code above) shows how to select between different
   * autonomous modes using the dashboard. The sendable chooser code works with the Java
   * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the chooser code and
   * uncomment the getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to the switch structure
   * below with additional strings. If using the SendableChooser make sure to add them to the
   * chooser code above as well.
   */
  @Override
  public void autonomousInit() {
m_chooser.setDefaultOption(name:"Default Auto", kDefaultAuto);
m_chooser.addOption(name:"My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);

    m_rightMotor.setInverted(true);
 System.out.println("Auto selected: " + m_autoSelected);
   mTimer.start();
   mTimer.reset();
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
   switch (m_autoSelected) {
      case kCustomAuto:
         backServo.setPosition(pos:1);
        System.out.println("Running custom auto");
        break;
      case kDefaultAuto:
      default:
        if(mTimer.get() < 2.65) { //1- Drive Forward 20 inches ( and stow servo inside)
            mDrive.tankDrive(leftSpeed:.5,rightSpeed:.5);
            backServo.setPosition(pos:1);
          }

          else if(mTimer.get()<3.15) {//2- Turn 90 degrees to the right
            mDrive.tankDrive(leftSpeed:.7, rightSpeed:-.7);


          }
          else if(mTimer.get()<6.0) {//3 - back up to the "goal"
            mDrive.tankDrive(leftSpeed:-.5, rightSpeed:-.5);


          }

          else if(mTimer.get()<8.0){

            mDrive.tankDrive(leftSpeed:0, rightSpeed:0);
            backServo.setPosition(pos:1);
          }

          //System.out.println("Running default auto");
          else {//step end the last one
            mDrive.tankDrive(leftSpeed:0, rightSpeed:0);
          }
          break;
      }
    }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {}

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    double leftSpeed = -m_controller.getLeftY();

    m_leftMotor.set(leftSpeed);

  double rightSpeed = -m_controller.getRightY();
    m_rightMotor.set(rightSpeed);


     if (m_controller.getAButton()) {
       m_leftMotor.set(0.5);
     } else {
       m_leftMotor.set(0);
     } 

     if (m_controller.getBButton()){
       m_rightMotor.set(0.5);
     } else {
       m_rightMotor.set(0);
     }

     if (m_controller.getXButton()) {
       m_leftMotor.set(-0.5);
     } else { 
       m_leftMotor.set(0);
       }

     if (m_controller.getYButton()) {
       m_rightMotor.set(-0.5);
     } else {
       m_rightMotor.set(0);
     }
  }


  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {}

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {}

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}
}
