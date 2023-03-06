package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;

public class ConeHoldCommand extends CommandBase {
    private Intake m_Intake;

    public ConeHoldCommand(Intake intake) {
        m_Intake = intake;
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public void execute() {
        m_Intake.coneHold();
    }
}
