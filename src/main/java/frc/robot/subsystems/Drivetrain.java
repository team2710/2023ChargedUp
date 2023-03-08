package frc.robot.subsystems;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;


public class Drivetrain extends SubsystemBase  {

    
    private WPI_TalonFX m_rightParent, m_leftParent, m_rightChild, m_leftChild;
    public DifferentialDrive differentialDrive;

    public double maxPower;




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

        differentialDrive = new DifferentialDrive(m_leftParent, m_rightParent);
        addChild("Differential Drive",differentialDrive);
        differentialDrive.setSafetyEnabled(true);
        differentialDrive.setExpiration(0.1);
        differentialDrive.setMaxOutput(1.0);

        m_rightParent.setNeutralMode(NeutralMode.Brake);
        m_rightChild.setNeutralMode(NeutralMode.Brake);
        m_leftParent.setNeutralMode(NeutralMode.Brake);
        m_leftChild.setNeutralMode(NeutralMode.Brake);
    }




    public void speedDrive(double drive, double turn, int mode){
        // speed mode: 0 default, 1 speed up, 2 speed down

        System.out.println(mode);
        // maxPower = 1;
        if (mode == 1){
            maxPower = 1.0;
        }else if (mode == 2){
            maxPower = 0.2;
        }else{
            maxPower = 0.6;
        }


        double jsDeadBand = Constants.OperatorConstants.kControllerDeadzone;

        double drivePower = 0;
        double turnPower = 0;

        drivePower =  Math.abs(drive) >= jsDeadBand ? -maxPower * Math.pow(drive, 3) : 0;
        turnPower =  Math.abs(turn) >= jsDeadBand ? -maxPower * Math.pow(turn, 3) * 0.8 : 0; //the turn is a little more sensitive
        
        
        // System.out.println(drivePower + "         "+ turnPower);
        differentialDrive.curvatureDrive(drivePower, turnPower, true);



    }

    public void driveStraight(double speed){
        differentialDrive.tankDrive(-speed, -speed);
    }





    
}