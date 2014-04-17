#!D:/Perl/bin/perl.exe -w
use CGI;
use Fcntl qw(:DEFAULT :flock);
use File::Temp qw/ tempfile tempdir /;

$docroot = "$ENV{'DOCUMENT_ROOT'}";
$settingsfile =  "$docroot/cgi-bin/upload_settings.inc";

if (-e $settingsfile)
{
    #Execute settingsfile (must be valid perl code)
    do $settingsfile;
}

if(!(-e $settingsfile) or !$max_upload or !$tmp_dir)
{
    #Default setting values
    $max_upload = 200000000;
    $tmp_dir="c:\\upload\\uploadtemp";
}
   

@qstring = split(/&/,$ENV{'QUERY_STRING'});
@p1 = split(/=/,$qstring[0]);
@p2 = split(/=/,$qstring[1]);

if("test" eq $p1[0])
{
    print "Content-type: text/html\n\n";
    print "Perl seems to be installed and working!<br />";
    print "<b>Settings</b><br/>";
    print "Max upload size: ".$max_upload."<br />";
    print "Temp dir:        ".$tmp_dir."<br />";

    exit;
}

#DATA
$sessionid = $p1[1];
$sessionid =~ s/[^a-zA-Z0-9]//g;

$filename = $p2[1];

$len = $ENV{'CONTENT_LENGTH'};
$bRead=0;

#setting
$post_data_file = "$tmp_dir/$sessionid"."_postdata";
$monitor_file   = "$tmp_dir/$sessionid"."_flength";
$signal_file    = "$tmp_dir/$sessionid"."_signal";
$name_file      = "$tmp_dir/$sessionid"."_name";
 

sub break
{
    $mes = shift;
    print "Content-type: text/html\n\n";
    print "<html><head><body><h1>Error</h1>Encountered error: $mes</body></html>\n";
    exit;
}


if($len > $max_upload)
{
    close (STDIN);
    break("The maximum upload size has been exceeded");
}

open(SIG,">","$signal_file");
print SIG "false";
close(SIG);

open(NMF,">","$name_file");
print NMF $filename;
close(NMF);

open(FH,">",$monitor_file ) or &break ("Can't open numfile: $!");
print FH $len;
close(FH);

open(TMP,">","$post_data_file") 
 or &break ("Can't open temp file");
 
binmode TMP;

my $i=0;
$BUFFER = "";
$LINE = "";
$PRINTLINE = "";
  
while (read (STDIN ,$LINE, 10000))
{
    $bRead += length $LINE;

    if($i == 0)
    {
        $ind    = index($LINE,"\r\n",0);
        $flag   = substr($LINE,0,$ind);
        $ind    = index($LINE,"name=\"file",$ind);
        $ind    = index($LINE,"\r\n\r\n",$ind);
        $LINE   = substr($LINE,$ind+4);
        $i++;
    }
	
    $ind = index($BUFFER.$LINE,$flag,0);
  
    if($ind!=-1)
    {
        $PRINTLINE = substr($BUFFER.$LINE,0,$ind-2);
        print TMP $PRINTLINE,"";
    } else
    {
        print TMP $BUFFER,"";
        $BUFFER = $LINE;
    }
}

close (TMP);

open(SIG,">","$signal_file");
print SIG "true";
close(SIG);
 
print "Content-type: text/html\n\n";
print "<html><body>END</body></html>";

exit;
	