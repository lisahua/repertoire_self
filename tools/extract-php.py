from php_tester import php_initializer
from sys import argv

a = php_initializer()
a.extract_tests(argv[1], argv[2]);
