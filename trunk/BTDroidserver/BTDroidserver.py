#!/usr/bin/env python
#/usr/bin/python
#Base in this example: http://code.google.com/p/pybluez/wiki/Documentation
from bluetooth import *
import os

result=os.popen("hcitool dev | grep -c hci ").read()
#print result
if result=="0\n":
	print "Bluetooth Device not found"
	exit(1)

	
while True:
	server_sock=BluetoothSocket( RFCOMM )
	server_sock.bind(("",PORT_ANY))
	server_sock.listen(1)
	
	port = server_sock.getsockname()[1]
	
	uuid = "94f39d29-7d6d-437d-973b-fba39e49d4ee"
	
	advertise_service( server_sock, "SampleServer",
					   service_id = uuid,
					   service_classes = [ uuid, SERIAL_PORT_CLASS ],
					   profiles = [ SERIAL_PORT_PROFILE ],
	#				   protocols = [ OBEX_UUID ]
						)
					   
	os.system("clear")
	print "Waiting for connection on RFCOMM channel %d" % port
	client_sock, client_info = server_sock.accept()
	print "Accepted connection from ", client_info
	
	posX=500
	posY=500
	comando="xdotool mousemove "
	
	try:
		while True:
			data = client_sock.recv(1024)
			if len(data) == 0: break
			print "received [%s]" % data
		#---multimedia-----------------------------------
			if data=="play":
				os.system("xdotool key \"XF86AudioPlay\"")
			elif data=="next":
				os.system("xdotool key \"XF86AudioNext\"")
			elif data=="prev":
				os.system("xdotool key \"XF86AudioPrev\"")
			elif data=="volmas":
				os.system("xdotool key \"XF86AudioRaiseVolume\"")
			elif data=="volmenos":
				os.system("xdotool key \"XF86AudioLowerVolume\"")
			elif data=="mute":
				os.system("xdotool key \"XF86AudioMute\"")
			elif data=="stop":
				os.system("xdotool key \"XF86AudioStop\"")
			elif data=="music":
				os.system("xdotool key \"XF86AudioMusic\"")
		#---mouse-------------------------------------
			elif data=="mright":
				os.system("xdotool click 1")
			elif data=="mleft":
				os.system("xdotool click 3")
			elif data.startswith("movement"):
				if len(data)<30:
					cortado=data.split()
					posX+=int(cortado[1])
					posY+=int(cortado[3])
					com=comando+" "+str(posX)+" "+str(posY)
					os.system(com)
				
			elif data=="gow":
				posX+=10
				com=comando+" "+str(posX)+" "+str(posY)
				os.system(com)
			elif data=="goe":
				print data
				posX-=10
				com=comando+" "+str(posX)+" "+str(posY)
				os.system(com)
			elif data=="gon":
				posY+=10
				com=comando+" "+str(posX)+" "+str(posY)
				os.system(com)
			elif data=="gos":
				posY-=10
				com=comando+" "+str(posX)+" "+str(posY)
				os.system(com)
		#---desktop--------------------------------
			elif data=="internet":
				os.system("xdotool key \"XF86WWW\"")
			elif data=="cambiar":
				os.system("xdotool key \"Alt+Tab\"")
	
	except IOError:
		pass
	
	print "disconnected"
	
	client_sock.close()
	server_sock.close()
print "all done"
