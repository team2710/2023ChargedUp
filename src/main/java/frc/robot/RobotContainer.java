package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.ArmCommand;
import frc.robot.commands.ArmMoveCommand;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.ElevatorCommand;
import frc.robot.commands.ElevatorMoveCommand;
import frc.robot.commands.IntakeCommand;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Drivetrain.SpeedMode;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class RobotContainer {
    public static Drivetrain drivetrain;
    public static Elevator elevator;
    public static Arm arm;
    public static Intake intake;
    
    private static CommandXboxController driverController;
    private static CommandXboxController auxController;

    public static void init() {
        drivetrain = new Drivetrain(
            Constants.DriveConstants.kRightParentTalon, Constants.DriveConstants.kLeftParentTalon, 
            Constants.DriveConstants.kRightChildTalon, Constants.DriveConstants.kLeftChildTalon, false, true);
        elevator = new Elevator(Constants.ElevatorConstants.kLeftSparkmax, Constants.ElevatorConstants.kRightSparkmax);
        arm = new Arm(Constants.ArmConstants.kArmTalonSRX);
        intake = new Intake(Constants.ArmConstants.kIntakeSparkmax);

        driverController = new CommandXboxController(Constants.OperatorConstants.kDriverControllerPort);
        auxController = new CommandXboxController(Constants.OperatorConstants.kAuxControllerPort);

        drivetrain.setDefaultCommand(new DriveCommand(drivetrain, driverController));
        elevator.setDefaultCommand(new ElevatorCommand(elevator, auxController));
        arm.setDefaultCommand(new ArmCommand(arm, intake, auxController));

        configureButtonBindings();
    }

    public static void configureButtonBindings() {
        Trigger rightBumper = auxController.rightBumper();
        Trigger leftBumper = auxController.leftBumper();
        rightBumper.onTrue(new ElevatorMoveCommand(elevator, Constants.ElevatorConstants.kElevatorMax)
            .withInterruptBehavior(Command.InterruptionBehavior.kCancelSelf)
            .alongWith(new RunCommand(() -> {
                drivetrain.setSpeedMode(SpeedMode.DOWN);
            }, drivetrain)));
        leftBumper.onTrue(new ElevatorMoveCommand(elevator, 0)
            .withInterruptBehavior(Command.InterruptionBehavior.kCancelSelf)
            .andThen(new RunCommand(() -> {
                drivetrain.setSpeedMode(SpeedMode.DEFAULT);
            }, drivetrain)));

        Trigger bButton = auxController.b();
        Trigger aButton = auxController.a();
        bButton.onTrue(new ArmMoveCommand(arm, 4800));
        aButton.onTrue(new ArmMoveCommand(arm, 0));

        Trigger driverLeftBumper = driverController.leftBumper();
        driverLeftBumper.whileTrue(new RunCommand(() -> {
            drivetrain.setSpeedMode(SpeedMode.UP);
        }, drivetrain));
        Trigger driverRightBumper = driverController.rightBumper();
        driverRightBumper.whileTrue(new RunCommand(() -> {
            drivetrain.setSpeedMode(SpeedMode.DOWN);
        }, drivetrain));


        Trigger yButton = auxController.y(); // Intake Cone
        Trigger xButton = auxController.x(); // Intake Cube
        Trigger povUpButton = auxController.povUp(); // Hold Cone
        Trigger povDownButton = auxController.povDown(); // Stop Intake
        yButton.onTrue(new IntakeCommand(intake, 25, Constants.ArmConstants.kIntake));
        xButton.onTrue(new IntakeCommand(intake, 25, Constants.ArmConstants.kIntake * -1));
        povUpButton.onTrue(new IntakeCommand(intake, 5, Constants.ArmConstants.kIntakeHold));
        povDownButton.onTrue(new IntakeCommand(intake, 25, 0));
    }

    public static void runAutos() {
        CommandScheduler.getInstance().schedule(
            new ArmMoveCommand(arm, 4800)
            .alongWith(new ElevatorMoveCommand(elevator,Constants.ElevatorConstants.kElevatorMax)
            .andThen(new IntakeCommand(intake, 25, 0.25)
            .andThen(new WaitCommand(0.5)
            .andThen(new IntakeCommand(intake, 25, 0))
            ))));
    }
}
