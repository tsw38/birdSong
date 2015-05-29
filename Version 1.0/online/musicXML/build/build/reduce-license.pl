#!/usr/bin/perl -w
# reduce-license.pl --- script for reduce licenses
use warnings;
use strict;

our $command = 'java -jar ./support/compiler.jar ' .
  '--compilation_level WHITESPACE_ONLY ' .
  '--create_source_map ./js_mapfiles/scorediv-latest.map ' .
  '--js ./public/scorediv-compiled.js ' .
  '--js_output_file ./public/scorediv-latest.js';

system($command);

our $license = "/*
 This file is part of
 score-library <http://www.musicxml-viewer.com>.
 author & contact: XiongWenjie (navigator117 at gmail.com)
 score-library is free software:
 you can redistribute it and/or modify it under the terms of the
 GNU General Public License as published by the Free Software Foundation,
 either version 3 of the License, or (at your option) any later version.

 score-library is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 See the GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with score-library.
 If not, see <http://www.gnu.org/licenses>.
*/\n";

open(my $file, '>>', './public/scorediv-latest.js') or die $!;
print($file $license);
close($file);
