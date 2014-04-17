#!D:/Perl/bin/perl.exe -w

use CGI;
use Fcntl qw(:DEFAULT :flock);
use File::Temp qw/ tempfile tempdir /;

print "Content-type: text/html\n\n";
print time().$$;
exit;