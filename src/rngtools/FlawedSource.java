package rngtools;


// this class accepts an rng and adds a flaw
abstract public class FlawedSource extends RNG
{
IRNG rng;

  protected FlawedSource() {}
  protected FlawedSource( IRNG source )
  {
    rng = source;
  }


  // a type one flaw has bits in the flawmask always set or always clear, depending on set
  public static IRNG create( IRNG source, int flawmask, boolean set )
  {
    return new FlawTypeOne( source, flawmask, set );
  }


  // a type two flaw has bitb = bita (true) or bitb = !bita (false), depending on correlation
  public static IRNG create( IRNG source, int bita, int bitb, boolean correlation )
  {
    return new FlawTypeTwo( source, bita, bitb, correlation );
  }


  @Override
  public void setState( byte[] b )
  {
    rng.setState( b );
  }


  @Override
  public boolean seek( long position )
  {
    return rng.seek( position );
  }


  @Override
  public void advance( long amount )
  {
    rng.advance( amount );
  }


  @Override
  public boolean isSeekable()
  {
    return rng.isSeekable();
  }


  @Override
  public int blockSize()
  {
    return 4;
  }


  @Override
  public long nextl( int bits )
  {
    if (bits <= 32)
      return next( bits ) | 0L;

    return ((nextl( 32 ) << 32) | nextl( 32 )) >>> (64-bits);
  }


  abstract public IRNG deepCopy();


  protected IRNG deepCopy( IRNG target )
  {
    if (target instanceof FlawedSource)
    {
    FlawedSource fs = (FlawedSource) target;

      fs.rng = rng.deepCopy();
    }

    return target;
  }

}


class FlawTypeOne extends FlawedSource
{
int mask;
boolean setAction;


  protected FlawTypeOne() {}
  FlawTypeOne( IRNG source, int flawmask, boolean set )
  {
    super( source );

    mask = flawmask;
    setAction = set;
  }


  @Override
  public int next( int bits )
  {
  int ret = rng.next( bits );

    if (setAction)
      ret |= mask;
    else
      ret &= ~mask;

    return ret & ((1<<bits) - 1);
  }


  @Override
  public IRNG deepCopy()
  {
    return deepCopy( new FlawTypeOne() );
  }


  @Override
  protected IRNG deepCopy( IRNG target )
  {
    super.deepCopy( target );
    if (target instanceof FlawTypeOne)
    {
    FlawTypeOne fto = (FlawTypeOne) target;

      fto.mask = mask;
      fto.setAction = setAction;
    }

    return target;
  }
}


class FlawTypeTwo extends FlawedSource
{
int bita, bitb;
boolean correlation;


  protected FlawTypeTwo() {}
  FlawTypeTwo( IRNG source, int bita, int bitb, boolean correlation )
  {
    super( source );

    this.bita = bita;
    this.bitb = bitb;
    this.correlation = correlation;
  }


  boolean getBit( int from, int which )
  {
    return 0 != (from & 1<<which);
  }


  int setBit( int from, int which, boolean towhat )
  {
    if (towhat)
      return from | 1<<which;

    return from & ~(1<<which);
  }


  @Override
  public int next( int bits )
  {
  int ret = rng.next( bits );

    if (correlation)
      ret = setBit( ret, bitb, getBit( ret, bita ));
    else
      ret = setBit( ret, bitb, !getBit( ret, bita ));

    return ret & ((1 << bits) - 1);
  }


  @Override
  public IRNG deepCopy()
  {
    return deepCopy( new FlawTypeTwo() );
  }


  @Override
  protected IRNG deepCopy( IRNG target )
  {
    super.deepCopy( target );
    if (target instanceof FlawTypeTwo)
    {
    FlawTypeTwo ftt = (FlawTypeTwo) target;

      ftt.bita = bita;
      ftt.bitb = bitb;
      ftt.correlation = correlation;
    }

    return target;
  }
}

