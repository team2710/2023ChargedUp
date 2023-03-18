package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Arm;

public class ZeroArm extends SequentialCommandGroup {
    private Arm m_Arm;

    public ZeroArm(Arm arm) {
        addRequirements(arm);
        m_Arm = arm;

        addCommands(
            new InstantCommand(() -> {
                m_Arm.zero();
            }),
            new WaitCommand(2),
            new InstantCommand(() -> {
                m_Arm.resetEncoder();
            })
        );
    }
}
