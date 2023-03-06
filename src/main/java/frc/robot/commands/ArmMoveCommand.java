package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Arm;

public class ArmMoveCommand extends CommandBase {
    private Arm m_Arm;
    private double m_Position;

    public ArmMoveCommand(Arm arm, double position) {
        m_Arm = arm;
        m_Position = position;
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public void execute() {
        System.out.println(m_Position);
        m_Arm.setPosition(m_Position);
    }
}
