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


  // a level one flaw has bits in the flawmask always set or always clear, depending on set
  ISeekableRNG createLevelOne( ISeekableRNG source, int flawmask, boolean set )
  {
    return null;
  }

  // a level two flaw has bitb = bita (true) or bitb = !bita (false), depending on correlation
  ISeekableRNG createLevelTwo( ISeekableRNG source, int bita, int bitb, boolean correlation )
  {
    return null;
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
    // TODO Auto-generated method stub
    return null;
  }

}
