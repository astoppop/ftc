package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class FullDrive extends LinearOpMode {
    /* "https://firstroboticsbc.org/ftc/ftc-team-resources/training-tensorflow-for-centerstage/" */
    /* https://firstroboticsbc.org/ftc/ftc-team-resources/centerstage-autonomous-programs/ */
    /* https://github.com/acharraggi/Centerstage-Samples */
    private DcMotor FrontLeft;
    private DcMotor FrontRight;
    private DcMotor BackLeft;
    private DcMotor BackRight;

    @Override
    public void runOpMode() {

        /* Drive Train Setup */
        FrontLeft = hardwareMap.get(DcMotor.class, "FL");
        FrontRight = hardwareMap.get(DcMotor.class, "FR");
        BackLeft = hardwareMap.get(DcMotor.class, "BL");
        BackRight = hardwareMap.get(DcMotor.class, "BR");

        FrontLeft.setDirection(DcMotor.Direction.FORWARD);
        BackLeft.setDirection(DcMotor.Direction.FORWARD);
        FrontRight.setDirection(DcMotor.Direction.REVERSE);
        BackRight.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();

        while (opModeIsActive()) {

            /* Mecanum Drive Control */
            double Rstrafe = 0.0;
            double Lstrafe = 0.0;

            if (gamepad1.dpad_left) {
                Lstrafe = 0.8;
            } else if (gamepad1.dpad_right) {
                Rstrafe = -0.8;
            }

            double max;
            double rotate = -gamepad1.right_stick_x;
            double strafe = Lstrafe + Rstrafe;
            double drive = gamepad1.left_stick_y;

            double[] speeds = {
                    (drive + strafe + rotate),
                    (drive - strafe - rotate),
                    (drive - strafe + rotate),
                    (drive + strafe - rotate)
            };

            max = Math.max(Math.abs(speeds[0]), Math.abs(speeds[1]));
            max = Math.max(max, Math.abs(speeds[2]));
            max = Math.max(max, Math.abs(speeds[3]));

            if (max > 1.0) {
                speeds[0] /= max;
                speeds[1] /= max;
                speeds[2] /= max;
                speeds[3] /= max;
            }

            FrontLeft.setPower(speeds[0]);
            FrontRight.setPower(speeds[1]);
            BackLeft.setPower(speeds[2]);
            BackRight.setPower(speeds[3]);

            telemetry.addData("Welcome to the REAL SLAM Shady Driver Station", null);
            telemetry.update();
        }
    }
}
