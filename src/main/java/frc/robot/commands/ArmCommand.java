package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Intake;

public class ArmCommand extends CommandBase {

    private final Arm arm;
    private final Intake intake;
    private final CommandXboxController auxController;

    public ArmCommand(Arm arm, Intake intake, CommandXboxController auxController) {
        this.arm = arm;
        this.auxController = auxController;
        this.intake = intake;
        addRequirements(arm);
        addRequirements(intake);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void initialize() {
        // elevator.setPosition(7.5);
        SmartDashboard.putNumber("Arm Set Position", 0);
    }

    @Override
    public void execute() {
        double leftY = auxController.getRawAxis(1);
        intake.setSpeed(leftY * 0.5);

        double rightY = auxController.getRawAxis(3);
        if (Math.abs(rightY) >= 0.1)
            arm.setSpeed(rightY * 0.2);
        // double position = SmartDashboard.getNumber("Arm Set Position", 0);

        // arm.setPosition(position);
        SmartDashboard.putNumber("Arm Angle", arm.getAngle());
    }
    
}
