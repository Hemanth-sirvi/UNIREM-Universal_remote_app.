package com.sirvi.unirem_v1.models

import android.content.Context
import android.hardware.ConsumerIrManager

class IrController(context: Context) {

    val irMan =
        context.getSystemService(Context.CONSUMER_IR_SERVICE)
                as? ConsumerIrManager
    fun buildVoltasPacket(
        currentAcState: AcState
    ): ByteArray {

        val data = ByteArray(10)

        // Byte 0 --> Horizontal swing, 0x33 for on and 0x32 for off
        var b0 = 0x33
        if(currentAcState.isSwingON) b0 = b0 or 0x32
        data[0]  = b0.toByte()

        // Byte 1 --> mode + fan speed
        // (0)Chill mode + auto = 0xE8 | Cool mode + auto = 0xE2   | (1)Dry + Fixed  = 0x84
        // (0)Chill mode + low = 0x88  | Cool mode + low = 0x82    | (2)Fan + High = 0x21
        // (0)Chill mode + mid = 0x48  | Cool mode + mid = 0x42
        // (0)Chill mode + high = 0x28 | Cool mode + high = 0x22
        var b1 = 0xE8
        b1 = when((currentAcState.mode*10) + (currentAcState.speed)){
            0-> 0xE8
            1-> 0x88
            2-> 0x48
            3-> 0x28
            10,11,12,13-> 0x84
            20,21,22,23 -> 0x21
            else -> 0xE8
        }

        data[1] = b1.toByte()

        // Byte 2 powerON 0x28 powerOff 0x00 / 0x80?
        var b2 = 0x28
        if (currentAcState.isPowerON) b2 = b2 or 0x80

        data[2] = b2.toByte()

        // Byte 3
        // 24C
        var b3 = 0x12
        b3 = when(currentAcState.temperature){
            18-> 0x12
            19 -> 0x13
            20 -> 0x14
            21 -> 0x15
            22 -> 0x16
            23 -> 0x17
            24 -> 0x18
            25 -> 0x19
            26 -> 0x1A
            27 -> 0x1B
            28 -> 0x1C
            29 -> 0x1D
            30 -> 0x1E
            else -> 0x18

        }
        data[3] = b3.toByte()

        // Remaining default bytes
        data[4] = 0x3B
        data[5] = 0x3B
        data[6] = 0x3B
        data[7] = 0x11
        data[8] = 0x00

        // Checksum
        var sum = 0

        for (i in 0 until 9) {
            sum += data[i].toInt() and 0xFF
        }

        data[9] = (sum.inv() and 0xFF).toByte()

        return data
    }

    fun encodeVoltas(data: ByteArray): IntArray {

        val raw = mutableListOf<Int>()

        for (byte in data) {

            for (i in 7 downTo 0) {

                val bit = (byte.toInt() shr i) and 1

                raw.add(1000)

                if (bit == 1)
                    raw.add(2570)
                else
                    raw.add(580)
            }
        }

        // Final stop pulse
        raw.add(1000)

        return raw.toIntArray()
    }


    fun sendPower(currentAcState: AcState) {

        val packet = buildVoltasPacket(currentAcState = currentAcState)

        val pattern = encodeVoltas(packet)

        this.irMan?.transmit(
            38000,
            pattern
        )
    }

    fun acStateChanged(
        currentAcState: AcState){
        val packet = buildVoltasPacket(currentAcState)
        val pattern = encodeVoltas(packet)
        this.irMan?.transmit(
            38000,
            pattern
        )
    }


}