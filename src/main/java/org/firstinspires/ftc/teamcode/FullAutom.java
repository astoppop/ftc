package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.CameraName;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.ExposureControl;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.GainControl;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;


import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import android.util.Size;
import java.util.List;
import java.util.concurrent.TimeUnit;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.ElapsedTime.Resolution;

@Autonomous
public class FullAutom extends LinearOpMode {

    private int backdropTagID = 0;
    private VisionPortal visionPortal;
    private AprilTagProcessor aprilTag;
    private AprilTagDetection desiredTag = null;
    private boolean targetFound = false;

    private WebcamName Cam;

    private ElapsedTime runtime = new ElapsedTime();

    private TfodProcessor tfod;
    final String TFOD_MODEL_FILE = "";
    public static final String[] LABELS = {"blue", "red"};

    @Override
    public void runOpMode() {
        int currentStep = 1;
        waitForStart();

        visionPortal.stopLiveView();
        runtime.reset();
        while (opModeIsActive()) {
            targetFound = false;
            desiredTag  = null;
        }
    }

    private void initVisionPortal() {
        Cam = hardwareMap.get(WebcamName.class, "Cam");

        tfod = new TfodProcessor.Builder()
                .setModelFileName(TFOD_MODEL_FILE)
                .setModelLabels(LABELS)
                .build();

        aprilTag = new AprilTagProcessor.Builder().build();
        aprilTag.setDecimation(3);

        visionPortal = new VisionPortal.Builder()
                .setCamera(Cam)
                .addProcessor(aprilTag)
                .addProcessor(tfod)
                .build();
    }
}
