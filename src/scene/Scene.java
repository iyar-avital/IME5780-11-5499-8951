package scene;

import elements.AmbientLight;
import elements.Camera;
import elements.LightSource;
import geometries.Geometries;
import geometries.Intersectable;
import primitives.Color;

import java.util.LinkedList;
import java.util.List;

/**
 * Scene class
 */
public class Scene {
    /**
     * name of scene
     */
    private String _name;
    /**
     * background in scene
     */
    private Color _backGround;
    /**
     * ambientLight in scene
     */
    private AmbientLight _ambientLight;
    /**
     * all the geometries in scene
     */
    private Geometries _geometries;
    /**
     * camera in scene
     */
    private Camera _camera;
    /**
     * the distance betwwen the camera and view
     */
    private double _distance;
    /**
     * all ths lights in scene
     */
    private List<LightSource> _lights;
    /**
     * scene constructor received the name scene
     * @param name - the scene name
     */
    public Scene(String name)
    {
        _name = name;
        _geometries = new Geometries();
        _lights = new LinkedList<LightSource>();
    }

    public String get_name() {
        return _name;
    }

    public Color get_background() {
        return new Color(_backGround);
    }

    public AmbientLight get_ambientLight() {
        return _ambientLight;
    }

    public Geometries get_geometries() {
        return _geometries;
    }

    public Camera get_camera() {
        return _camera;
    }

    public double get_distance() {
        return _distance;
    }

    public List<LightSource> get_lights() { return _lights; }

    public void set_background(Color _backGround) {
        this._backGround = _backGround;
    }

    public void set_ambientLight(AmbientLight _ambientLight) {
        this._ambientLight = _ambientLight;
    }

    public void set_camera(Camera _camera) {
        this._camera = _camera;
    }

    public void set_distance(double _distance) {
        this._distance = _distance;
    }


    public void addGeometries(Intersectable... geometries) {
        for (Intersectable g : geometries) {
            _geometries.add(g);
        }
    }
    public void addLights(LightSource ... lights) {
        for (LightSource l : lights) {
            _lights.add(l);
        }
    }
}
