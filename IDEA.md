

```scala

model {
  
  'a = 1.0
  'b = 2.0
   
  foreach(k) {
    'phi ~: Dir('b)
  }
  
  foreach(d in docs)  {
    'theta ~: Dir('a)
  
    foreach(w in words) {
      'z ~: Mult('theta)
      'v ~: Mult('phi('z))
    }
  }

  hints {
    collapse('theta, 'z)
    collapse('w, 'phi)
  }

}


Root {
  Param(Ident("a"))
  Param(Ident("b"))
  Plate(Ident("k"), Range) {
    RV(Ident("phi"), Dir(Ident("b")))
  }
  Plate(Ident("d"), Iterator) {
    RV(Ident("theta"), Dir(Ref("a")))
    Plate(Ident("w"), Iterator) {
      RV(Ident("z"), Mult(Ref("theta")))
      RV(Ident("v"), Mult(Ref("phi", "z")))
    }
  }
}

```

Usage:

```scala

val model = ModelDescription.compile
model.apply(a = 1.0, b = 2.0, k = 10, d = doc.words)

```

