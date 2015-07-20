/*
 * Copyright (c) 2015 Oracle and/or its affiliates. All rights reserved. This
 * code is released under a tri EPL/GPL/LGPL license. You can use it,
 * redistribute it and/or modify it under the terms of the:
 *
 * Eclipse Public License version 1.0
 * GNU General Public License version 2
 * GNU Lesser General Public License version 2.1
 */
package org.jruby.truffle.nodes.objects;

import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.source.SourceSection;
import org.jruby.truffle.nodes.RubyNode;
import org.jruby.truffle.runtime.RubyContext;
import org.jruby.truffle.runtime.core.RubyBasicObject;
import org.jruby.truffle.runtime.core.RubyClass;

/**
 * Reads the internal metaclass of an object.
 */
@NodeChild(value="object", type=RubyNode.class)
public abstract class MetaClassNode extends RubyNode {

    public MetaClassNode(RubyContext context, SourceSection sourceSection) {
        super(context, sourceSection);
    }

    public abstract RubyBasicObject executeMetaClass(VirtualFrame frame, Object value);

    @Specialization(guards = "value")
    protected RubyBasicObject singletonClassTrue(boolean value) {
        return getContext().getCoreLibrary().getTrueClass();
    }

    @Specialization(guards = "!value")
    protected RubyBasicObject singletonClassFalse(boolean value) {
        return getContext().getCoreLibrary().getFalseClass();
    }

    @Specialization
    protected RubyBasicObject singletonClass(int value) {
        return getContext().getCoreLibrary().getFixnumClass();
    }

    @Specialization
    protected RubyBasicObject singletonClass(long value) {
        return getContext().getCoreLibrary().getFixnumClass();
    }

    @Specialization
    protected RubyBasicObject singletonClass(double value) {
        return getContext().getCoreLibrary().getFloatClass();
    }

    @Specialization
    protected RubyBasicObject singletonClass(RubyBasicObject object) {
        return object.getMetaClass();
    }

}
