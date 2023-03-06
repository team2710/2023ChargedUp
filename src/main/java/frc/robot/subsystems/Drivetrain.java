package frc.robot.subsystems;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

public class Drivetrain extends SubsystemBase {
    private WPI_TalonFX m_rightParent, m_leftParent,
                    m_rightChild, m_leftChild;
    // private DifferentialDrive differentialDrive;

    public Drivetrain(int rightTalonParentID, int leftTalonParentID, int rightTalonChildID, int leftTalonChildID, 
                    boolean rightInversion, boolean leftInversion) {
        m_rightParent = new WPI_TalonFX(rightTalonParentID);
        m_leftParent = new WPI_TalonFX(leftTalonParentID);
        m_rightChild = new WPI_TalonFX(rightTalonChildID);
        m_leftChild = new WPI_TalonFX(leftTalonChildID);
        m_rightChild.follow(m_rightParent);
        m_leftChild.follow(m_leftParent);

        // m_rightParent.configOpenloopRamp(.25);
        // m_leftParent.configOpenloopRamp(.25);

        m_rightParent.setInverted(rightInversion);
        m_rightChild.setInverted(rightInversion);
        m_leftParent.setInverted(leftInversion);
        m_leftChild.setInverted(leftInversion);

        // differentialDrive = new DifferentialDrive(m_leftParent, m_rightParent);
    }

    public void setPower(double rightPower, double leftPower) {
        setRightPower(rightPower);
        setLeftPower(leftPower);
    }

    public void setRightPower(double power) {
        m_rightParent.set(ControlMode.PercentOutput, power);
    }
    
    public void setLeftPower(double power) {
        m_leftParent.set(ControlMode.PercentOutput, power);
    }

    public void setBrakeMode() {
        m_rightParent.setNeutralMode(NeutralMode.Brake);
        m_rightChild.setNeutralMode(NeutralMode.Brake);
        m_leftParent.setNeutralMode(NeutralMode.Brake);
        m_leftChild.setNeutralMode(NeutralMode.Brake);
    }

    public void setCoastMode() {
        m_rightParent.setNeutralMode(NeutralMode.Coast);
        m_rightChild.setNeutralMode(NeutralMode.Coast);
        m_leftParent.setNeutralMode(NeutralMode.Coast);
        m_leftChild.setNeutralMode(NeutralMode.Coast);
    }

    public void arcadeDrive(double moveSpeed, double rotateSpeed) {
        // differentialDrive.arcadeDrive(moveSpeed, rotateSpeed);
        
    }

    public void tankDrive(double left, double right) {
        if (Math.abs(left) > 0.1) left = 0.1 * Math.signum(left);
        if (Math.abs(right) > 0.1) right = 0.1 * Math.signum(right);
        setLeftPower(left);
        setRightPower(right);
    }
}
