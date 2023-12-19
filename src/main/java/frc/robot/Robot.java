// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.motorcontrol.PWMVictorSPX;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PneumaticsModuleType;

public class Robot extends TimedRobot {
  private final PWMVictorSPX leftFrontMotor = new PWMVictorSPX(0);
  private final PWMVictorSPX leftBackMotor = new PWMVictorSPX(2);
  private final PWMVictorSPX rightFrontMotor = new PWMVictorSPX(3);
  private final PWMVictorSPX rightBackMotor = new PWMVictorSPX(1);
  private final CANSparkMax armMotor = new CANSparkMax(1, MotorType.kBrushless);
  private final Compressor compressor = new Compressor(PneumaticsModuleType.CTREPCM);
  private final DoubleSolenoid piston = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 0, 1);
  private final Joystick leftController = new Joystick(0);
  private final Joystick rightController = new Joystick(1);

  @Override
  public void teleopPeriodic() {
    double leftX = Math.abs(leftController.getX()) < 0.1 ? 0 : leftController.getX();
    double leftPower = MathUtil.clamp(leftController.getY() + leftX, -1, 1);
    double rightPower = MathUtil.clamp(leftController.getY() - leftX, -1, 1);
    leftBackMotor.set(-leftPower);
    leftFrontMotor.set(-leftPower);

    rightBackMotor.set(rightPower);
    rightFrontMotor.set(rightPower);

    piston.set(rightController.getTrigger() ? DoubleSolenoid.Value.kForward : DoubleSolenoid.Value.kReverse);

    if (rightController.getRawButton(2)) {
      compressor.disable();
    }

    armMotor.set(rightController.getY() * 0.2);

    SmartDashboard.putNumber("joystick y", leftController.getY());
    SmartDashboard.putNumber("joystick x", leftController.getX());
    SmartDashboard.putNumber("left power", leftPower);
    SmartDashboard.putNumber("right power", rightPower);
    SmartDashboard.putNumber("Left front power", leftFrontMotor.get());
    SmartDashboard.putNumber("Left back power", leftBackMotor.get());
    SmartDashboard.putNumber("Right front power", rightFrontMotor.get());
    SmartDashboard.putNumber("Right back power", rightBackMotor.get());

    SmartDashboard.putNumber("Arm power", armMotor.get());
    SmartDashboard.putNumber("Arm Encoder", armMotor.getEncoder().getPosition());
  }

  @Override
  public void disabledInit() {
    compressor.disable();
  }

  @Override
  public void disabledExit() {
    // compressor.enableDigital();
    armMotor.getEncoder().setPosition(0);
  }
}
