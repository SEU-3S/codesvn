#!D:/Perl/bin/perl.exe -w
use CGI;
use Fcntl qw(:DEFAULT :flock);
use File::Temp qw/ tempfile tempdir /;

$docroot = "$ENV{'DOCUMENT_ROOT'}";

#setting file 
$settingsfile =  "$docroot/cgi-bin/upload_settings.inc";
$percentUpload = 0;

if (-e $settingsfile)
{
    #Execute settingsfile (must be valid perl code)
    do $settingsfile;

}

if(!(-e $settingsfile) or !$tmp_dir)
{
    #Default setting values
    $tmp_dir="c:\\upload\\uploadtemp";
}
 
read (STDIN ,$qs, 100);
@p1 = split(/=/, $qs);
  
$sessionid = $p1[1];
$sessionid =~ s/[^a-zA-Z0-9]//g;
 
open SIG, "+< $tmp_dir/$sessionid"."_signal" or &break;
read(SIG,$signal,10);
close(SIG); 
	 
if(index($signal,"false",0)!=-1)
{
    open FL, "+< $tmp_dir/$sessionid"."_flength" or &break;
    read (FL,$fl, 1000);
    close(FL);

    open FTMP,"+< $tmp_dir/$sessionid"."_postdata" or &break;
    @inode = stat(FTMP); 
    close(FTMP);
    $tmpfl = $inode[7];
  	  
    $percentUpload = int(($tmpfl/$fl)*100);
   
}
else
{
    open FNM ,"+< $tmp_dir/$sessionid"."_name";
    read(FNM,$filename,1000);
    close(FNM);
    $filename = unescape("$upload_dir/$filename");
	  
    #rename file	  
    rename ($tmp_dir."/$sessionid"."_postdata", $filename);
  
    #delete temp files	  
    unlink ("$tmp_dir/$sessionid"."_postdata");
    unlink ("$tmp_dir/$sessionid"."_flength");
    unlink ("$tmp_dir/$sessionid"."_signal");
    unlink ("$tmp_dir/$sessionid"."_name");
	    
    $percentUpload = -1;
		
}
	
sub break
{
    print "Content-type: text/html\n\n";
    print 0; 
    exit;
}


sub unescape {
    my $escaped = shift;
    $escaped =~ s/%u((?:[0-9a-f]{2})+)/chr(hex($1))/eig;
    $escaped =~ s/%([0-9a-f]{2})/chr(hex($1))/eig;
    return $escaped;
}
 
print "Content-type: text/html\n\n";
print $percentUpload;

exit;
