package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
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
    }

    @Override
    public void execute() {
        // double leftY = auxController.getRawAxis(1);
        // intake.setSpeed(-leftY);

        if (intake.getMotorTemperature() > Constants.ArmConstants.kMaxIntakeMotorTemperature) {
            System.out.println("MOTOR TEMPERATURE TOO HIGH, ABORTING");
            // intake.stop();
        }

        // System.out.println(intake.getOutputCurrent());

        // if (intake.getOutputCurrent() > 20) {
        //     System.out.println("CONE DETECTED LOL");
        //     CommandScheduler.getInstance().schedule(new IntakeCommand(intake, 5, Constants.ArmConstants.kIntakeHold));
        // }

        double rightY = auxController.getRawAxis(5);
        // if (Math.abs(rightY) >= 0.1)
            // arm.setPosition(arm.getRawEncoder()+100*Math.signum(rightY));
            // arm.setPower(rightY * 0.1);
        // double position = SmartDashboard.getNumber("Arm Set Position", 0);

        // arm.setPosition(position);
        SmartDashboard.putNumber("Arm Angle", arm.getAngle());
    }
    
}
