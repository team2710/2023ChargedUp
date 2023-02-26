package frc.robot;

public final class Constants {
    public static class OperatorConstants {
        public static final int kDriverControllerPort = 0;
        public static final int kAuxControllerPort = 0;
        public static final double kControllerDeadzone = 0.01;
    }

    public static class DriveConstants {
        public static final int kLeftParentTalon = 3;
        public static final int kLeftChildTalon = 2;
        public static final int kRightParentTalon = 1;
        public static final int kRightChildTalon = 0;
    }

    public static class ElevatorConstants {
        public static final int kLeftSparkmax = 16;
        public static final int kRightSparkmax = 17;
        public static final double kElevatorMax = 8.57;
    }
}
