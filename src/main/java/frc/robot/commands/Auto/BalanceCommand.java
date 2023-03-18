package frc.robot.commands.Auto;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class BalanceCommand extends CommandBase {
    private Drivetrain m_Drivetrain; 
    private Boolean isbalancing = false;

    public BalanceCommand(Drivetrain drivetrain) {
        m_Drivetrain = drivetrain;
        addRequirements(drivetrain);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void initialize() {
        // m_Drivetrain.driveStraight(0.1);
    }

    @Override
    public void execute() {
        if (Math.abs(m_Drivetrain.getRoll()) > 5) {
            m_Drivetrain.driveStraight(0.5 * Math.signum(m_Drivetrain.getRoll()));
            isbalancing = true;
            SmartDashboard.putBoolean("Is Balancing", true);
        } else if(isbalancing) {
            m_Drivetrain.driveStraight(0);
            SmartDashboard.putBoolean("Is Balancing", false);
        }
    }
}