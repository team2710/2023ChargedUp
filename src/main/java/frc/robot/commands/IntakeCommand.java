package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;

public class IntakeCommand extends CommandBase {
    private Intake m_Intake;
    private int m_CurrentLimit;
    private double m_Speed;

    public IntakeCommand(Intake intake, int currentLimit, double speed) {
        m_Speed = speed;
        m_CurrentLimit = currentLimit;
        m_Intake = intake;
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public void execute() {
        m_Intake.setSpeed(m_Speed);
        m_Intake.setCurrentLimit(m_CurrentLimit);
    }
}
