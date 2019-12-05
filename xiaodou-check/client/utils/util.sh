sendMail(){
	/usr/local/bin/sendEmail -f ${email_sender} -t ${email_reciver} -s ${email_smtphost} -u $1 -xu ${email_username} -xp ${email_password} -m $2 
}