package rngtools;


// this class accepts an rng and adds a flaw
public class FlawedSource extends SeekableRNG
{
ISeekableRNG rng;

  protected FlawedSource() {}
  protected FlawedSource( ISeekableRNG source )
  {
    rng = source;
  }


  // a type one flaw has bits in the flawmask always set or always clear, depending on set
  ISeekableRNG createTypeOne( ISeekableRNG source, int flawmask, boolean set )
  {
    return new FlawTypeOne( source, flawmask, set );
  }

  // a type two flaw has bitb = bita (true) or bitb = !bita (false), depending on correlation
  ISeekableRNG createTypeTwo( ISeekableRNG source, int bita, int bitb, boolean correlation )
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
  public int next32()
  {
    // TODO Auto-generated method stub
    return 0;
  }


  @Override
  public ISeekableRNG deepCopy()
  {
    return deepCopy( new FlawedSource() );
  }


  @Override
  protected ISeekableRNG deepCopy( ISeekableRNG target )
  {
    // todo: copy rng
    return target;
  }

}


class FlawTypeOne extends FlawedSource
{
int mask;
boolean setAction;

  FlawTypeOne( ISeekableRNG source, int flawmask, boolean set )
  {
    super( source );

    mask = flawmask;
    setAction = set;
  }


  @Override
  public int next32()
  {
  int ret = super.next32();

    if (setAction)
      ret |= mask;
    else
      ret |= ~mask;

    return ret;
  }
}


class FlawTypeTwo extends FlawedSource
{
int bita, bitb;
boolean correlation;


  FlawTypeTwo( ISeekableRNG source, int bita, int bitb, boolean correlation )
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

    return from | ~(1<<which);
  }


  @Override
  public int next32()
  {
  int ret = super.next32();

    if (correlation)
      ret = setBit( ret, bitb, getBit( ret, bita ));
    else
      ret = setBit( ret, bitb, !getBit( ret, bita ));

    return ret;
  }
}

