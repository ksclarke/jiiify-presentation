
//
// An antlr4 grammar for JSON Pointer
//

grammar JsonPointer;

pointer
   : ( '/' token )*
   ;
token
   : ( UNESCAPED | ESCAPED )*
   ;
UNESCAPED
   : [\u0000-\u{002E}] | [\u0030-\u{007D}] | [\u007F-\u{10FFFF}]
   ;
ESCAPED
   : '~' ( '0' | '1' )
   ;
