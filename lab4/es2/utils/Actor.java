//##########################################// 
// # ACTOR                                  //
//##########################################// 
// + A generic object able to communicate   //
//   on TCP connections                     //
//##########################################//  

public interface Actor {
    void Snd();
    void Rcv();
}
