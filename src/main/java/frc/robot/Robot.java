package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends TimedRobot {
    private RobotContainer robotContainer;

    private final SendableChooser<String> scoringAutoChooser = new SendableChooser<>();
    private final SendableChooser<String> taxiAutoChooser = new SendableChooser<>();

    @Override
    public void robotInit() {
        robotContainer = new RobotContainer();
        RobotContainer.arm.resetEncoder();

        scoringAutoChooser.setDefaultOption("Cube Mid", "Cube Mid");
        scoringAutoChooser.addOption("Cube Low", "Cube Low");
        taxiAutoChooser.setDefaultOption("Taxi", "Taxi");
        taxiAutoChooser.setDefaultOption("Auto Balance", "Auto Balance");
        taxiAutoChooser.setDefaultOption("No Taxi", "No Taxi");

        SmartDashboard.putData("Scoring Auto Chooser", scoringAutoChooser);
        SmartDashboard.putData("Taxi Auto Chooser", taxiAutoChooser);
    }

    @Override
    public void disabledPeriodic() {
    }

    @Override
    public void testInit() {
    }

    @Override
    public void testPeriodic() {
    }

    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();
        robotContainer.updateSmartdashboard();
    }

    @Override
    public void teleopInit() {
        robotContainer.runTeleop();
    }

    @Override
    public void teleopPeriodic() {

    }

    @Override
    public void autonomousInit() {
        robotContainer.runAutos(scoringAutoChooser.getSelected(), taxiAutoChooser.getSelected());
    }

    @Override
    public void autonomousPeriodic() {
    }
}
