package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Elevator;

public class ElevatorMoveCommand extends CommandBase {
    private Elevator m_Elevator;
    private double m_Position;

    public ElevatorMoveCommand(Elevator elevator, double position) {
        m_Elevator = elevator;
        m_Position = position;
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public void execute() {
        m_Elevator.setPosition(m_Position);
    }
}
