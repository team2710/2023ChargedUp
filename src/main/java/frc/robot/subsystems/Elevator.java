package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Elevator extends SubsystemBase {
    private CANSparkMax m_LeftMotor, m_RightMotor;
    private RelativeEncoder m_Encoder;
    private SparkMaxPIDController m_PidController;
    private double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput;

    public Elevator(int leftSparkmaxID, int rightSparkmaxID) {
        m_LeftMotor = new CANSparkMax(leftSparkmaxID, MotorType.kBrushless);
        m_RightMotor = new CANSparkMax(rightSparkmaxID, MotorType.kBrushless);
        m_LeftMotor.restoreFactoryDefaults();
        m_RightMotor.restoreFactoryDefaults();
        m_RightMotor.follow(m_LeftMotor);
        m_PidController = m_LeftMotor.getPIDController();
        m_Encoder = m_LeftMotor.getEncoder();

        kP = 0.27; 
        kI = 0;
        kD = 0; 
        kIz = 0; 
        kFF = 0; 
        kMaxOutput = 0.15; 
        kMinOutput = -0.15;

        m_PidController.setP(kP);
        m_PidController.setI(kI);
        m_PidController.setD(kD);
        m_PidController.setIZone(kIz);
        m_PidController.setFF(kFF);
        m_PidController.setOutputRange(kMinOutput, kMaxOutput);
    }

    void setGains(double p, double i, double d, double Iz, double FF) {
        kP = p;
        kI = i;
        kD = d;
        kIz = Iz;
        kFF = FF;
        m_PidController.setP(kP);
        m_PidController.setI(kI);
        m_PidController.setD(kD);
        m_PidController.setIZone(kIz);
        m_PidController.setFF(kFF);
    }

    public void setPosition(double height) {
        m_PidController.setReference(height, CANSparkMax.ControlType.kPosition);
    }

    public double getEncoderValue() {
        return m_Encoder.getPosition();
    }
}
