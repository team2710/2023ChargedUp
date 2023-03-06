package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;

public class ConeReleaseCommand extends CommandBase {
    private Intake m_Intake;

    public ConeReleaseCommand(Intake intake) {
        m_Intake = intake;
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public void initialize() {
        m_Intake.coneRelease();
    }
}
