package rngtools;


public class IntegerSourceByteStream extends RNGStream
{
  protected IntegerSourceByteStream() {}
  protected IntegerSourceByteStream( ISeekableRNG rng )
  {
    super( rng );
  }


  public IntegerSourceByteStream create()
  {
    return null;
  }

  public IntegerSourceByteStream create( int maxBytes )
  {
    return null;
  }
}

