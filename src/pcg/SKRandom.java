package pcg;

import java.util.Random;

import rngtools.RNG;


/*
 * Seekable Kernel Random.
 *  Drop in replacement for java.util.Random
 *
 * Copyright 2015 Charles Griffiths <cs.griffiths@yahoo.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

public class SKRandom extends Random
{
private static final long serialVersionUID = 1L;

RNG source;
long seed;


  public SKRandom()
  {
    if (null == source) source = new PCG32();
  }


  public SKRandom( long seed )
  {
    super( seed );
    if (null == source) source = new PCG32( seed );
  }


  public SKRandom( RNG rng )
  {
    source = rng;
    source.setState( seed );
  }


  @Override
  protected int next( int bits )
  {
    return source.next( bits );
  }


  @Override
  public void setSeed( long seed )
  {
    super.setSeed( seed );

    if (null == source)
      source = new PCG32();

    source.setState( seed );
    this.seed = seed;
  }
}

