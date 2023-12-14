// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.motorcontrol.PWMVictorSPX;

import com.datasiqn.robotutils.controlcurve.ControlCurve;
import com.datasiqn.robotutils.controlcurve.ControlCurves;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PneumaticsModuleType;

public class Robot extends TimedRobot {
  private PWMVictorSPX leftFrontMotor = new PWMVictorSPX(0);
  private PWMVictorSPX leftBackMotor = new PWMVictorSPX(2);
  private PWMVictorSPX rightFrontMotor = new PWMVictorSPX(3);
  private PWMVictorSPX rightBackMotor = new PWMVictorSPX(1);
  private Compressor compressor = new Compressor(PneumaticsModuleType.CTREPCM);
  private DoubleSolenoid piston = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 0, 1);
  private Joystick leftController = new Joystick(0);

  private final ControlCurve<?, ?> driveCurve = ControlCurves.power(3)
      .withMinimumPower(0.1)
      .withDeadZone(0.1)
      .build();

  private XboxController controller = new XboxController(2);



  @Override
  public void teleopPeriodic() {
    double controllerLeft = controller.getLeftY();
    double controllerRight = controller.getRightY();
    leftBackMotor.set(driveCurve.get(controllerLeft) * -0.5);
    leftFrontMotor.set(driveCurve.get(controllerLeft) * -0.5);

    rightBackMotor.set(driveCurve.get(controllerRight) * 0.5);
    rightFrontMotor.set(driveCurve.get(controllerRight) * 0.5);
    //extends the pyston
    if (leftController.getRawButton(6)) {
      piston.set(DoubleSolenoid.Value.kForward);
    }
    //contracts the pyston
    if (leftController.getRawButton(4)) {
      piston.set(DoubleSolenoid.Value.kReverse);
    }
    //emergeny shutoff for the presure 
    if (leftController.getRawButton(3)){
      compressor.disable();
    }
  }

  @Override
  public void disabledInit() {
    compressor.disable();

  }

  @Override
  public void disabledExit() {
    compressor.enableDigital();
  }
}
