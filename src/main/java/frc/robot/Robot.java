// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.motorcontrol.PWMVictorSPX;

public class Robot extends TimedRobot {
  private PWMVictorSPX leftFrontMotor = new PWMVictorSPX(0);
  private PWMVictorSPX leftBackMotor = new PWMVictorSPX(2);
  private PWMVictorSPX rightFrontMotor = new PWMVictorSPX(3);
  private PWMVictorSPX rightBackMotor = new PWMVictorSPX(1);

  private XboxController controller = new XboxController(2);

  @Override
  public void robotInit() {
    
  }

  @Override
  public void robotPeriodic() {
    
  }

  @Override
  public void teleopInit() {

  }

  @Override
  public void teleopPeriodic() {
    double controllerLeft = controller.getLeftY();
    double controllerRight = controller.getRightY();

    leftBackMotor.set(controllerLeft * 0.5);
    leftFrontMotor.set(controllerLeft * 0.5);

    rightBackMotor.set(controllerRight * 0.5);
    rightFrontMotor.set(controllerRight * 0.5);
  }

  @Override
  public void disabledInit() {

  }

  @Override
  public void disabledExit() {
    
  }
}
