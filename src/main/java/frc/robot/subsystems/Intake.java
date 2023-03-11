package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {
    // WPI_TalonSRX m_IntakeTalonSRX;
    CANSparkMax m_IntakeSparkMax;

    public Intake(int intakeTalonSRXID) {
        // m_IntakeTalonSRX = new WPI_TalonSRX(intakeTalonSRXID);
        m_IntakeSparkMax = new CANSparkMax(intakeTalonSRXID, MotorType.kBrushless);
        m_IntakeSparkMax.setSmartCurrentLimit(25);
    }

    public double getOutputCurrent() {
        return m_IntakeSparkMax.getOutputCurrent();
    }

    public void setCurrentLimit(int currentLimit) {
        m_IntakeSparkMax.setSmartCurrentLimit(currentLimit);
    }

    public void intake(int direction) {
        setCurrentLimit(25);
        setSpeed(direction * 0.5);
    }

    public void hold(int direction) {
        setCurrentLimit(5);
        setSpeed(direction * 0.07);
    }

    public void stop() {
        setSpeed(0);
    }

    public void setSpeed(double speed) {
        System.out.println(m_IntakeSparkMax.getMotorTemperature());
        m_IntakeSparkMax.set(speed);
    }

    public double getMotorTemperature() {
        return m_IntakeSparkMax.getMotorTemperature();
    }
}
