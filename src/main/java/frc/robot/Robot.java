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
        // robotContainer.setCoastMode();
        robotContainer.resetArm();

        scoringAutoChooser.setDefaultOption("Cube Mid", "Cube Mid");
        scoringAutoChooser.addOption("Cube Low", "Cube Low");
        taxiAutoChooser.setDefaultOption("Taxi", "Taxi");
        taxiAutoChooser.setDefaultOption("No Taxi", "No Taxi");

        SmartDashboard.putData("Scoring Auto Chooser", scoringAutoChooser);
        SmartDashboard.putData("Taxi Auto Chooser", taxiAutoChooser);
    }

    @Override
    public void testPeriodic() {
        robotContainer.periodic();
    }

    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();
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
