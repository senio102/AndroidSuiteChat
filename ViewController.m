#import "ViewController.h"

@interface UIViewController ()

@end

@implementation ViewController

- (void)viewDidLoad {
    //[viewDidLoad];
    //call networkComm class whenever the storyboard view is loaded
    [self networkComm];

}


- (void)networkComm {
    //Core Foundation stream to connect to remote host
    CFReadStreamRef readIn;
    CFWriteStreamRef writeOut;
    //brings two different streams to port
    CFStreamCreatePairWithSocketToHost(NULL, "Enter IP", "port", &readIn, &writeOut);
    input = (__bridge NSInputStream *)readIn;
    output = (__bridge NSOutputStream *)writeOut;
    
    //use delegate to recieve callback/notification of streams
    [input setDelegate:self];
    [output setDelegate:self];
    //scedule streams in run loop
    [input scheduleInRunLoop:[NSRunLoop currentRunLoop] forMode:NSDefaultRunLoopMode];
    [output scheduleInRunLoop:[NSRunLoop currentRunLoop] forMode:NSDefaultRunLoopMode];
}

- (IBAction)joinServer:(id)sender {
    //setup a join message to send once connected
    NSString *response  = [NSString stringWithFormat:@"username.text"];
    //data variable to format the string
    NSData *data = [[NSData alloc] initWithData:[response dataUsingEncoding:NSASCIIStringEncoding]];
    //write data to the stream
    [output write:[data bytes] maxLength:[data length]];
    
}
@end
