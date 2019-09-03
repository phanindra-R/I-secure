package com.example.i_secure


import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import org.eclipse.paho.client.mqttv3.MqttException
import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.MqttMessage
import java.io.UnsupportedEncodingException
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        val connectButton = findViewById<Button>(R.id.connect)
        connectButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {

                val editText = findViewById<EditText>(R.id.editText)

                if (!(editText.text.isNullOrEmpty())) {

                    val ipAddress = "tcp://" + editText.text.toString() + ":1883"
                    val client = MqttClient(ipAddress, "YOUR CLIENT ID", MemoryPersistence())
                    val options = MqttConnectOptions()
                    client.connect(options)
                    val status = findViewById<TextView>(R.id.connection_status)
                    status.text = "conneced."
                    status.setTextColor(getResources().getColor(R.color.colorPrimary))
                }
                else {
                    Toast.makeText(this@MainActivity, "Enter valid IP address.", Toast.LENGTH_LONG).show()
                }
            }
        })


        val ledValue = findViewById<SeekBar>(R.id.seekBar3)
        ledValue.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onProgressChanged(p0: SeekBar?, progress: Int, p2: Boolean) {
                val topic = "test/led"
                val payload = progress.toString()
                var encodedPayload = ByteArray(0)
                try {
                    encodedPayload = payload.toByteArray(charset("UTF-8"))
                    val message = MqttMessage(encodedPayload)
                    //client.publish(topic, message)
                } catch (e: UnsupportedEncodingException) {
                    e.printStackTrace()
                } catch (e: MqttException) {
                    e.printStackTrace()
                }

                Toast.makeText(this@MainActivity, "Led value : " + progress.toString(), Toast.LENGTH_LONG).show()
            }
        })

        val lockSwitch = findViewById<Switch>(R.id.switch1)
        lockSwitch?.setOnCheckedChangeListener { _, isChecked ->
            val msg = if (isChecked) "Door Locked." else "Door Unlocked."

            if (msg=="Door Locked."){
                val topic = "test/door"
                val payload = "Lock"
                var encodedPayload = ByteArray(0)
                try {
                    encodedPayload = payload.toByteArray(charset("UTF-8"))
                    val message = MqttMessage(encodedPayload)
                    //client.publish(topic, message)
                } catch (e: UnsupportedEncodingException) {
                    e.printStackTrace()
                } catch (e: MqttException) {
                    e.printStackTrace()
                }
            }

            if (msg=="Door Unlocked."){
                val topic = "test/door"
                val payload = "Unlock"
                var encodedPayload = ByteArray(0)
                try {
                    encodedPayload = payload.toByteArray(charset("UTF-8"))
                    val message = MqttMessage(encodedPayload)
                    //client.publish(topic, message)
                } catch (e: UnsupportedEncodingException) {
                    e.printStackTrace()
                } catch (e: MqttException) {
                    e.printStackTrace()
                }
            }
            Toast.makeText(this@MainActivity, msg, Toast.LENGTH_SHORT).show()

        }

    }
}


